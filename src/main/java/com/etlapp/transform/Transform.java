package com.etlapp.transform;

public interface Transform<S, T> {
	
	T transform(S source) throws Exception;

}
