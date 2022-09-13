package com.muratsystems.enchecarrinho.domain.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -800170752496060259L;

	public ValidationException(String message) {
		super(message);
	}

}
