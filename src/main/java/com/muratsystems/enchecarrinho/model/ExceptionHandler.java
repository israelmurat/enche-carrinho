package com.muratsystems.enchecarrinho.model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	// Trata as exceptions lançadas em BusinessException
	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
