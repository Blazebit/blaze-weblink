package com.blazebit.weblink.core.api;

public class WeblinkException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WeblinkException() {
		super();
	}

	public WeblinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeblinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeblinkException(String message) {
		super(message);
	}

	public WeblinkException(Throwable cause) {
		super(cause);
	}

}
