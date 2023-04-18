package org.jsp.SuperMarket.exception;

public class AllException extends Exception {
	String msg="";
	
	public AllException(String msg) {
		this.msg=msg;
	}
	
	public String getmessage() {
		return msg;
	}


}
