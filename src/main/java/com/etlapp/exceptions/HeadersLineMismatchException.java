package com.etlapp.exceptions;

public class HeadersLineMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4164735614869179995L;
	
	public HeadersLineMismatchException() {
		super("Header's length is different from line's length.");
	}
}
