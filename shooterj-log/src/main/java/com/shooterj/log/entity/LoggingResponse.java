package com.shooterj.log.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoggingResponse {
	private int status;
	private Map<String, String> headers;
	private String body;
}