package com.gustavosass.orders.exceptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private String timestamp;
	private String message;
	private String details;
	
	public ExceptionResponse(String message, String details) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.timestamp = formatter.format(new Date());
		this.message = message;
		this.details = details;
	}

	@Override
	public String toString() {
	    return "{" +
	            "\"timestamp\":\"" + timestamp + "\"," +
	            "\"message\":\"" + message + "\"," +
	            "\"details\":\"" + details + "\"" +
	            "}";
	}
	
}