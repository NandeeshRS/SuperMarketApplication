package org.jsp.SuperMarket.helper;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	String message;
	int statuscode;
	T data;
}
