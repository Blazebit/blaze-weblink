package com.blazebit.weblink.core.api;

public class WeblinkSecurityGroupNotFoundException extends WeblinkException {

	private static final long serialVersionUID = 1L;

	public WeblinkSecurityGroupNotFoundException() {
		super();
	}

	public WeblinkSecurityGroupNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeblinkSecurityGroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeblinkSecurityGroupNotFoundException(String message) {
		super(message);
	}

	public WeblinkSecurityGroupNotFoundException(Throwable cause) {
		super(cause);
	}

}
