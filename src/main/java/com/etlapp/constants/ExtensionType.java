package com.etlapp.constants;

import java.util.Arrays;
import java.util.List;

public enum ExtensionType {
	CSV, XLSX;
	
	public String toString() {
		switch(this) {
		case CSV:
			return "csv";
		default:
			return "xlsx";
		}
	}
	
	public static List<String> allExtensions() {
		return Arrays.asList(CSV.toString(), XLSX.toString());
	}
}
