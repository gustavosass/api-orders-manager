package com.gustavosass.orders.exceptions;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String message;
	private String details;
	
	public ExceptionResponse(String message, String details) {
		this.timestamp = new Date();
		this.message = message;
		this.details = details;
	}
	
}