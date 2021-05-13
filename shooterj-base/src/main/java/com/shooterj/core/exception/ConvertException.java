package com.shooterj.core.exception;

import com.shooterj.core.util.ExceptionUtil;
import com.shooterj.core.util.WWStrUtil;

/**
 * 转换异常
 * @author shooterj
 */
public class ConvertException extends RuntimeException{
	private static final long serialVersionUID = 4730597402855274362L;

	public ConvertException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}
	
	public ConvertException(String message) {
		super(message);
	}
	
	public ConvertException(String messageTemplate, Object... params) {
		super(WWStrUtil.format(messageTemplate, params));
	}
	
	public ConvertException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ConvertException(Throwable throwable, String messageTemplate, Object... params) {
		super(WWStrUtil.format(messageTemplate, params), throwable);
	}
}
