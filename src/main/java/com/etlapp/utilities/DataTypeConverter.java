package com.etlapp.utilities;

import java.sql.Date;

/**
 * Utility class to conditionally try to parse a string to a field's data type
 * @author Leo Guercio
 */
public class DataTypeConverter {
	
	/**
	 * Convert's the String value into an instance of the rClass
	 * @param rClass Class of the setter method to be used by the reflector
	 * @param value The string value retrieved from a file
	 * @return Object's subclass. Depending on the rClass and vale, any of the boxed types or a Date
	 */
	public Object convertToParameterType(Class<?> rClass, String value) {
		//Switch cased to avoid the conundrum of using reflection
		switch(rClass.getName()) {
		case "Integer":
			return Integer.valueOf(value);
		case "Double":
			return Double.valueOf(value);
		case "Long":
			return Long.valueOf(value);
		case "Boolean":
			return Boolean.valueOf(value);
		case "Date":
			return Date.valueOf(value);
		default:
			return value;
		}
	}
}
