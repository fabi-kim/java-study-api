package com.springboot.api.common;

public class Constants {

  public enum ExceptionClass {
    LOCK("lock"),
    PRODUCT("product");

    private final String exceptionClass;

    ExceptionClass(String exceptionClass) {
      this.exceptionClass = exceptionClass;
    }

    public String getExceptionClass() {
      return exceptionClass;
    }

    public String toString() {
      return getExceptionClass() + "Exception";
    }
  }

  public static class RedisKey {

    public static final String PRODUCT = "PRODUCT";
    public static final String GRACEFUL = "GRACEFUL";

  }
}
