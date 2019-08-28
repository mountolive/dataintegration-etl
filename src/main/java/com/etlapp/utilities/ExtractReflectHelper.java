package com.etlapp.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A reflection-based utility class used to retrieve data either from a worksheet
 * or from another entity. It's intended for the Extraction level of the ETL.
 * @author Leo Guercio
 *
 * @param <T> Receiver/Sink's class, for the transformation
 */
public class ExtractReflectHelper<T> {
	private Map<String, String> paramMap;
	private DataTypeConverter dataTypeConverter;
	
	public ExtractReflectHelper() {
		paramMap = new HashMap<>();
		dataTypeConverter = new DataTypeConverter();
	}
	
	/**
	 * Creates a T entity (Sink's) from a S entity (Source's)
	 * @param source Source's instance. Of which the sink's data is being taken out of
	 * @param receiver Sink's instance. The one to receive the data taken out from Source's
	 * @return Returns a properly initialized receiver.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * Exception throwing is subject to Java's reflection API's standards
	 */
	public <S> T fromEntity(S source, T receiver) throws IllegalAccessException, IllegalArgumentException, 
	                                                     InvocationTargetException, NoSuchMethodException, 
	                                                     SecurityException {
		Method[] methods = source.getClass().getMethods();
		paramMap = convertToGetter(convertToSetter(paramMap));
		for(Method method : methods) {
			if("get".equals(method.getName().substring(0, 2))) {
				receiver.getClass().getMethod(paramMap.get(method.getName()))
				        		   .invoke((Object) receiver, method.invoke(source));
			}
		}
		return receiver;
	}
	
	/**
	 * Creates a T entity (Sink's) from Worksheet's data (Map<String, String>, read line with headers)
	 * @param receiver Sink's instance. The one to receive the data taken out from Source's
	 * @param line Map<String,String> the line retrieved from the worksheet, with its headers
	 * @return Returns a properly initialized receiver.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * Exception throwing is subject to Java's reflection API's standards
	 */
	public T fromWorksheet(T receiver, Map<String, String> line) throws IllegalAccessException, IllegalArgumentException, 
	                                                              InvocationTargetException, NoSuchMethodException, 
	                                                              SecurityException {
		paramMap = convertToSetter(paramMap);
		for(Map.Entry<String, String> row : line.entrySet()) {
			Method setter = receiver.getClass().getMethod(paramMap.get(row.getKey()));
			Class<?>[] types = setter.getParameterTypes();
			setter.invoke((Object) receiver, dataTypeConverter.convertToParameterType(types[0], row.getValue()));
		}
		return receiver;
	}
	
	/**
	 * Takes the paramMap and turns the keys into standard getter methods' strings
	 * @param paramMap
	 * @return new paramMap
	 */
	private Map<String, String> convertToGetter(Map<String, String> paramMap) {
		return paramMap.entrySet()
					   .stream()
				       .collect(Collectors.toMap(x -> toMethod(x.getKey(), true), 
				       		                     x -> x.getValue()));
	}
	
	/**
	 * Takes the paramMap and turns the keys into standard setter methods' strings
	 * @param paramMap
	 * @return new paramMap
	 */
	private Map<String, String> convertToSetter(Map<String, String> paramMap) {
		return paramMap.entrySet()
					   .stream()
				       .collect(Collectors.toMap(x -> x.getKey(), 
				       		                     x -> toMethod(x.getValue(), false)));
	}
	
	/**
	 * Converts string in getter/setter standard naming, depending on isGet boolean
	 * @param paramMap
	 * @param isGet if the method is meant to be turned into a getter (or setter is set to false)
	 * @return new paramMap
	 */
	private String toMethod(String paramName, boolean isGet) {
		String modifier = "set";
		if(isGet) modifier = "get";
		return modifier + 
	           paramName.substring(0, 1).toUpperCase() + 
	           paramName.substring(1);
	}
}
