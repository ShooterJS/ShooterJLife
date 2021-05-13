package com.shooterj.core.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException {

	private static final long serialVersionUID = 7599563847979668591L;

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Integer code, String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
	}


}
