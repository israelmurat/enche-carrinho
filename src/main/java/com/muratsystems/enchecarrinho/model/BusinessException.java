package com.muratsystems.enchecarrinho.model;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -8303818745183709403L;
	
	public BusinessException(String message) {
		super(message);
	}

}
