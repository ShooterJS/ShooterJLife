package com.shooterj.log.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoggingRequest {
	private String clientIp;
	private String method;
	private String path;
	private Map<String, String> params;
	private Map<String, String> headers;
	private String body;
}