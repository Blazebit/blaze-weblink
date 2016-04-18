package com.blazebit.weblink.core.api;

public class WeblinkGroupNotFoundException extends WeblinkException {
	
	private static final long serialVersionUID = 1L;

	public WeblinkGroupNotFoundException() {
		super();
	}

	public WeblinkGroupNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeblinkGroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeblinkGroupNotFoundException(String message) {
		super(message);
	}

	public WeblinkGroupNotFoundException(Throwable cause) {
		super(cause);
	}

}
