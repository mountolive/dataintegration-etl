package com.etlapp.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.etlapp.constants.ExtensionType;
import com.etlapp.exceptions.ReadingFileException;

/**
 * Reader class for Worksheets. So far it supports xlsx and csv.
 * This can be used either to retrieve the field to field mapping file (parameterToMap method)
 * or to read line by line, in the form of a Map<String,String> where the keys are the headers.
 * It's ment to be used in a try-with-resources scenario.
 * @author Leo Guercio
 *
 */
public class WorksheetReader implements AutoCloseable {
	private FileInputStream rootReader;
	private BufferedReader csvReader;
	private Iterator<Row> rowIterator;
	private String fileExtension;
	private String currentLine;
	private List<String> headers;
	private int headersSize;
	private int linePointer;
	
	/**
	 * The constructor would fail if the extension of the file is not supported
	 * @param file The CSV/XLSX (or other worksheet) file
	 * @throws FileNotFoundException If the file doesn't exist
	 * @throws ReadingFileException if the extension of the file is not supported 
	 */
	public WorksheetReader(File file) throws FileNotFoundException, ReadingFileException {
		if(!this.isProperExtension(file.getPath()))
			throw new ReadingFileException("File passed doesn't have an accepted extension");
		linePointer = 0;
		rootReader = new FileInputStream(file);
	}
	
	/**
	 * Returns a new line in the form of a Map<String,String> where the keys are the header's params
	 * @return Map<String,String> where the keys are the header's params. null if there are no more lines
	 * @throws ReadingFileException if there are repeated columns
	 */
	public Map<String, String> getNewLineWithHeader() throws ReadingFileException {
		buildHeaders();
		List<String> dataLine = readNewLine();
		if(dataLine != null) {
			Map<String,String> newLine = IntStream.range(0, headersSize)
												  .boxed()
												  .collect(Collectors.toMap(headers::get, dataLine::get));
			if(newLine.size() != headersSize) {
				throw new ReadingFileException("Resulting line's map has less values than original headers' size: Check for repeated columns");
			}
			return newLine;
		}
		return null;
	}
	 
	/**
	 * Creates a Map<String,String> where keys should be used as source's
	 * params and values as sink's params
	 * @return Map<String,String> parameter to parameter
	 * @throws ReadingFileException
	 */
	public Map<String, String> parameterToMap() throws ReadingFileException {
		List<String> parameterPair = readNewLine(); 
		Map<String, String> parameterMap = new HashMap<>();
		while(parameterPair != null) {
			linePointer++;
			if(parameterPair.size() != 2) {
				String message = String.format("Parameters must have 2 columns. Check the size of the file passed to the constructor. Line: %d", linePointer);
				throw new ReadingFileException(message);
			}
			parameterPair = readNewLine();
			parameterMap.put(parameterPair.get(0), parameterPair.get(1));
		}
		return parameterMap;
	}
	
	@Override
	public void close() throws Exception {
		rootReader.close();
	}

	/**
	 * Reads first line of the file and retrieves the file's headers accordingly
	 * @throws ReadingFileException if the file being read is in a different line from the first one
	 */
	private void buildHeaders() throws ReadingFileException {
		if(headers == null) {
			if(linePointer != 0) 
				throw new ReadingFileException("Line pointer is not placed on first line of the file");
			headers = readNewLine();
			headersSize = headers.size();
		}
	}
	
	/**
	 * Read process for a csv file
	 * @return List<String> values from the read line
	 * @throws IOException From the actual FileInputReader it uses
	 */
	private List<String> readCsvLine() throws IOException {
		currentLine = csvReader.readLine();
		if(currentLine != null) {
			return Arrays.asList(currentLine.split(","));
		} 

		return null;
	}
	
	/**
	 * Read process for a xlsx file
	 * @return List<String> values from the read line
	 * @throws IOException From the actual FileInputReader it uses
	 * 		   ReadingFileException if the file is empty
	 */
	private List<String> readXlsxLine() throws IOException, ReadingFileException {
		if(rowIterator == null) {
			XSSFWorkbook workbook = new XSSFWorkbook(this.rootReader);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			//FIXME watch-out!
			workbook.close();
			rowIterator = worksheet.iterator();
			if(rowIterator.hasNext()) {
				return retrieveXlsxLine();
			} else {
				throw new ReadingFileException("XLSX file was empty");
			}
		} else {
			if(rowIterator.hasNext()) {
				return retrieveXlsxLine();
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Parsing helper for xlsx files. Transform every value into a String
	 * @return List<String> line read
	 */
	private List<String> retrieveXlsxLine() {
		List<String> line = new ArrayList<>();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
		while(cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			switch(cell.getCellType()) {
			case BOOLEAN:
				line.add("" + cell.getBooleanCellValue());
				break;
			case NUMERIC:
				line.add("" + cell.getNumericCellValue());
				break;
			default:
				line.add(cell.getStringCellValue());
			}
		}
		return line;
	}

	/**
	 * Wrapper helper to read lines, regardless of the file's extension
	 * @return List<String> read line
	 * @throws ReadingFileException wrapping any failure in individual read methods
	 * used inside it.
	 */
	private List<String> readNewLine() throws ReadingFileException {
		List<String> line;
		try {
			switch(fileExtension) {
			case "xlsx":
				line = readXlsxLine();
				break;
			default:
				line = readCsvLine();
			}
		} catch(Exception e) {
			throw new ReadingFileException(String.format("There was an error while reading line: %s", e.getMessage()));
		}
		linePointer++;
		return line;
	}
	
	/**
	 * Checks whether the extension of the file passed is supported
	 * @param filename Relative path of the file passed
	 * @return boolean, if the file's extension is suported. See {@link ExtensionType}
	 */
	private boolean isProperExtension(String filename) {
		Optional<String> optExt = Optional.ofNullable(filename)
							   			  .filter(f -> f.contains("."))
							              .map(f -> f.substring(filename.lastIndexOf(".") + 1));
		if(optExt.isPresent()) {
			fileExtension = optExt.get();
			return ExtensionType.allExtensions().contains(fileExtension);	
		}
		return false;
	}
}
