package com.winway.demo.exception;

import org.springframework.http.HttpStatus;

public class BizException extends BaseException {

	private static final long serialVersionUID = 7599563847979668591L;

	public BizException(String message) {
		super(message);
	}

	public BizException(Integer code, String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
	}


}
