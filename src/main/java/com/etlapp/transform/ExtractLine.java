package com.etlapp.transform;

import java.util.Map;

public interface ExtractLine<T> {

	T extract(Map<String, String> line) throws Exception;
}
