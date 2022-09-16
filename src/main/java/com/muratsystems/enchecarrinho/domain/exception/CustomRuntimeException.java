package com.muratsystems.enchecarrinho.domain.exception;

public class CustomRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -800170752496060259L;

	public CustomRuntimeException(String message) {
		super(message);
	}

}
