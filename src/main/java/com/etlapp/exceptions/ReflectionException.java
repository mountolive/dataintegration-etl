package com.etlapp.exceptions;

public class ReflectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2498887950923407920L;
	
	public ReflectionException(String exceptionMessage) {
		super(String.format("A reflection error ocurred: %s", exceptionMessage));
	}
}
