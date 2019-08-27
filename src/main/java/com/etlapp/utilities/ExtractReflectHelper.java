package com.etlapp.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtractReflectHelper<T> {
	
	private Map<String, String> paramMap;
	private DataTypeConverter dataTypeConverter;
	
	public ExtractReflectHelper(Map<String, String> paramMap) {
		this.paramMap = paramMap;
		dataTypeConverter = new DataTypeConverter();
	}
	
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
	
	private Map<String, String> convertToGetter(Map<String, String> paramMap) {
		return paramMap.entrySet()
					   .stream()
				       .collect(Collectors.toMap(x -> toMethod(x.getKey(), true), 
				       		                     x -> x.getValue()));
	}
	
	private Map<String, String> convertToSetter(Map<String, String> paramMap) {
		return paramMap.entrySet()
					   .stream()
				       .collect(Collectors.toMap(x -> x.getKey(), 
				       		                     x -> toMethod(x.getValue(), false)));
	}
	
	private String toMethod(String paramName, boolean isGet) {
		String modifier = "set";
		if(isGet) modifier = "get";
		return modifier + 
	           paramName.substring(0, 1).toUpperCase() + 
	           paramName.substring(1);
	}
}
