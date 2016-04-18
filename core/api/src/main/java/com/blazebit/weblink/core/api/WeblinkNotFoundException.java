package com.blazebit.weblink.core.api;

public class WeblinkNotFoundException extends WeblinkException {
	
	private static final long serialVersionUID = 1L;

	public WeblinkNotFoundException() {
		super();
	}

	public WeblinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeblinkNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeblinkNotFoundException(String message) {
		super(message);
	}

	public WeblinkNotFoundException(Throwable cause) {
		super(cause);
	}

}
