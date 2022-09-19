package com.shooterj.retry.core.exception;


import com.shooterj.retry.core.api.ErrorCode;

public class DomainException extends RuntimeException {

  private ErrorCode errCode;
  private String msg;

  public DomainException(ErrorCode errCode) {
    this.errCode = errCode;
  }

  public DomainException withMsg(String msg) {
    this.msg = msg;
    return this;
  }

  public ErrorCode getErrCode() {
    return errCode;
  }

  public int getCode() {
    return errCode.getCode();
  }

  public String getMsg() {
    if (this.msg != null) {
      return this.msg;
    }
    return errCode.getMsg();
  }

}
