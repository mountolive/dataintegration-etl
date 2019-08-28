package com.etlapp.core;

import java.io.File;
import java.util.Map;

import com.etlapp.exceptions.MapperException;
import com.etlapp.exceptions.ReadingFileException;

/**
 * Class to be used as a basic construct for transorming a class in 
 * another. It can be used either for plain extraction (from jdbc, for example)
 * or for transformation in the Transform step of the ETL.
 * @author Leo Guercio
 *
 * @param <S> source's Class
 * @param <T> sink's Class
 */
public class Mapper<S,T> {
	private WorksheetReader dataReader;
	private Map<String, String> parametersMap;

	public Mapper() {}
	
	/**
	 * @param csvMapFile The config file, mapping from source to sink's params
	 * @param dataReader Reader to be used in order to retrieve the file's data (see {@link WorksheetReader} and its exceptions)
	 */
	public Mapper(File csvMapFile, WorksheetReader dataReader) {
		try(WorksheetReader reader = new WorksheetReader(csvMapFile)) {			
			parametersMap = reader.parameterToMap();
		} catch(Exception e) {
			throw new MapperException(e.getMessage());
		}
		this.dataReader = dataReader;
	}

	/**
	 * Converts a <S> entity into a <T> one, according to the logic of the transform method
	 * of the {@link Transform}
	 * @param source <S> to be transformed
	 * @param transformer needs to implement the transform method from {@link Transform}
	 * @return <T> instance
	 * @throws MapperException
	 */
	public T mapFromEntity(S source, Transform<S, T> transformer) throws MapperException {
		try {
			return transformer.transform(source);
		} catch(Exception e) {
			throw new MapperException(e.getMessage());
		}
	}
	
	/**
	 * Creates a <T> entity from a Worksheet file (see {@link WorksheetReader})
	 * @param extractor {@link ExtractLine} instance
	 * @return Resulting <T> instance
	 * @throws MapperException occurs when there's a problem with the initialization of the dataReader
	 * @throws ReadingFileException occurs when there's a problem reading a file
	 */
	public T mapFromWorksheet(ExtractLine<T> extractor) throws MapperException, ReadingFileException {
		if(dataReader == null) {
			throw new MapperException("dataReader parameter can't be null. Can't retrieve data");
		}
		Map<String, String> line = dataReader.getNewLineWithHeader();
		if(line == null) return null;
		if(parametersMap.size() != line.size()) {
			throw new MapperException("Line passed doesn't have the same number of parameters");
		}
		
		try {
			return extractor.extract(line);
		} catch(Exception e) {
			throw new MapperException(e.getMessage());
		}
	}
}
