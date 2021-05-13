package com.shooterj.core.exception;


import com.shooterj.core.util.ExceptionUtil;
import com.shooterj.core.util.WWStrUtil;

/**
 * 工具类异常
 * @author xiaoleilu
 */
public class UtilException extends RuntimeException{
	private static final long serialVersionUID = 8247610319171014183L;

	public UtilException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(String messageTemplate, Object... params) {
		super(WWStrUtil.format(messageTemplate, params));
	}

	public UtilException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public UtilException(Throwable throwable, String messageTemplate, Object... params) {
		super(WWStrUtil.format(messageTemplate, params), throwable);
	}
}
