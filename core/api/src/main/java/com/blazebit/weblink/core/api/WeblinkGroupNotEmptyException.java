package com.blazebit.weblink.core.api;

public class WeblinkGroupNotEmptyException extends WeblinkException {
	
	private static final long serialVersionUID = 1L;

	public WeblinkGroupNotEmptyException() {
		super();
	}

	public WeblinkGroupNotEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WeblinkGroupNotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeblinkGroupNotEmptyException(String message) {
		super(message);
	}

	public WeblinkGroupNotEmptyException(Throwable cause) {
		super(cause);
	}

}
