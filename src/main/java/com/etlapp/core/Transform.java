package com.etlapp.core;

public interface Transform<S, T> {
	
	T transform(S source) throws Exception;

}
