package com.etlapp.core;

import java.lang.reflect.ParameterizedType;

/**
 * Generic class instantiator (assumes no parameters).
 * From: https://stackoverflow.com/questions/1090458/instantiating-a-generic-class-in-java
 * and
 * https://xebia.com/blog/acessing-generic-types-at-runtime-in-java/
 * @author Leo Guercio
 *
 * @param <T> Generic class
 */
public class GenericFactory<T> {

	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public GenericFactory() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Creates an instance of the generic type
	 * @return Instance of T
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T build() throws InstantiationException, 
	                        IllegalAccessException {
		return clazz.newInstance();
	}
}
