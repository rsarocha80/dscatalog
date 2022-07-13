package com.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;

public class ExceptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Instant timestamp;
	
	private Integer status;
	
	private String error;
	
	private String massage;
	
	private String path;
	
	public ExceptionDTO() {}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	

}
