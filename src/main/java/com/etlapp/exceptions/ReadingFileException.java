package com.etlapp.exceptions;

/**
 * Exception class to be used with WorksheetReader
 * @author Leo Guercio
 *
 */
public class ReadingFileException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5212373150648719318L;
	
	public ReadingFileException(String message) {
		super(message);
	}
	
}
