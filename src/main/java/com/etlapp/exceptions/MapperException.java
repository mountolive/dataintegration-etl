package com.etlapp.exceptions;

public class MapperException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8448921112603020374L;
	
	public MapperException(String message) {
		super(String.format("Exception while transforming source to receiver: %s", message));
	}

}
