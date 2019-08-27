package com.etlapp.core;

import java.io.File;
import java.util.Date;

import com.etlapp.constants.ExtensionType;
import com.etlapp.constants.RetailType;

/**
 * Utility class used for retrieving previously download worksheet (see ExtensionType) from path.
 * All it does is building an URI and retrieving a supposedly present file
 * with the method getFileFromDate. FileNotFoundException should be handled
 * by consumer.
 * @author Leo Guercio
 *
 */
public class WorksheetRetriever {
	
	private String uri;
	private ExtensionType extension = ExtensionType.CSV;
	
	public WorksheetRetriever(ExtensionType extension) {
		this.extension = extension;
		this.uri = String.format("../../../../../resources/%s/", extension.toString());
	}
	
	/**
	 * It's possible to pass a custom base uri.
	 * @param uri baseUrl for the file previously downloaded
	 */
	public WorksheetRetriever(String uri, ExtensionType extension) {
		this.extension = extension;
		this.uri = uri;
	}
	
	/**
	 * Returns a File object, according to the date and the RetailType passed
	 * @param fromDate Date of the file to be read
	 * @param business The RetailType, either SALE, PURCHASE or LOGISTIC
	 * @return File object with created URI (see findFromDate method)
	 */
	public File getFileFromDate(Date fromDate, RetailType business) {
		return new File(findFromDate(fromDate, business));
	}

	/**
	 * String builder to get where the worksheet file should be
	 * @param fromDate Date of the file to be read
	 * @param business The RetailType, either SALE, PURCHASE or LOGISTIC
	 * @return URI of the file
	 */
	private String findFromDate(Date fromDate, RetailType business) {
		StringBuilder baseUrl = new StringBuilder(this.uri);
		switch(business) {
		case SALE:
			baseUrl.append(String.format("sales/sales-from-%t", fromDate));
			break;
		case PURCHASE:
			baseUrl.append(String.format("purchases/purchases-from-%t", fromDate));
			break;
		case LOGISTIC:
			baseUrl.append(String.format("logistic/logistics-from-%t", fromDate));
			break;
		default:
			baseUrl.append("example/example");
		}
		
		return baseUrl.append(String.format(".s%", extension.toString())).toString();
	}
}
