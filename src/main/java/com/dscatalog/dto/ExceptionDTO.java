package com.dscatalog.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ExceptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private List<String> detail;
	private String path;
	private LocalDate timestamp;
	
	public ExceptionDTO() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDetail() {
		return detail;
	}

	public void setDetail(List<String> detail) {
		this.detail = detail;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	

}
