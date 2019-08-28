package com.etlapp.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.etlapp.core.GenericFactory;
import com.etlapp.core.Mapper;
import com.etlapp.core.WorksheetReader;
import com.etlapp.exceptions.MapperException;
import com.etlapp.exceptions.ReadingFileException;
import com.etlapp.exceptions.ReflectionException;
import com.etlapp.transform.ExtractLine;
import com.etlapp.transform.Transform;

/**
 * Utility class meant to be used as a first step's extraction
 * @author Leo Guercio
 *
 * @param <S> Source's class
 * @param <T> Sink's class
 */
public class RawDataExtractor<S, T> {
	private Mapper<S, T> mapper;
	
	/**
	 * Creates a List<T> (sink's class) from a List<S> (source's class)
	 * @param source List<S>, sink's list
	 * @return List<T> sink's list
	 */
	public List<T> fromDataSource(List<S> source) {
		List<T> convertedList = new ArrayList<>();
		mapper = new Mapper<S, T>();
		Transform<S, T> transformer =  new Transform<S, T>() {
			@Override
			public T transform(S source) throws Exception {
				ExtractReflectHelper<T> reflektor = new ExtractReflectHelper<>();
				try {
					return reflektor.fromEntity(source, new GenericFactory<T>().build());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException | InstantiationException e) {
					throw new ReflectionException(e.getMessage());
				}
			}
		};
		for(S input : source) {
			convertedList.add(mapper.mapFromEntity(input, transformer));
		}
		return convertedList;
	}
	
	/**
	 * Creates a List<T> from the data on a worksheet
	 * @param worksheet File, either xlsx or csv. See {@link WorksheetReader}
	 * @return List<T>, sink's list
	 * @throws ReadingFileException 
	 * @throws FileNotFoundException 
	 */
	public List<T> fromWorksheet(File worksheet) {
		List<T> resultList = new ArrayList<>();
		T tmp;
		try(WorksheetReader sheetReader = new WorksheetReader(worksheet)) {
			mapper = new Mapper<S, T>(worksheet, sheetReader);
			ExtractLine<T> extractor = new ExtractLine<T>() {
				@Override
			  	public T extract(Map<String, String> line) throws Exception {
					ExtractReflectHelper<T> reflektor = new ExtractReflectHelper<>();
			  		return reflektor.fromWorksheet(new GenericFactory<T>().build(), line);
			  	}
			};
			while((tmp = mapper.mapFromWorksheet(extractor)) != null) {
				resultList.add(tmp);
			}
		} catch(Exception e) {
			throw new MapperException(e.getMessage());
		}
		return resultList;
	}
}
