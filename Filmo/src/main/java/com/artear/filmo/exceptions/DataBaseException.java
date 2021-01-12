package com.artear.filmo.exceptions;

import com.artear.filmo.exceptions.util.ErrorFilmo;

public class DataBaseException extends RuntimeException {

	private static final long serialVersionUID = -6949729861747322671L;

	private ErrorFilmo error;
	
	public DataBaseException(ErrorFilmo error) {
		super(error.getMensaje());
		this.error = error;
	}

	public ErrorFilmo getError() {
		return error;
	}

}
