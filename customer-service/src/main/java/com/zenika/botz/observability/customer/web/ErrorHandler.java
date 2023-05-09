package com.zenika.botz.observability.customer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String handleInternalError(Exception e) {
    if(e instanceof ResponseStatusException responseStatusException){
      throw responseStatusException;
    }
    LOGGER.error("internal server error", e);
    return String.format("Internal Server Error (traceId: %s)", MDC.get("X-B3-TraceId"));
  }

}
