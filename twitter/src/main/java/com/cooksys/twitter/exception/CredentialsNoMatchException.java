package com.cooksys.twitter.exception;

import javax.servlet.http.HttpServletResponse;

public class CredentialsNoMatchException extends Exception {

	public static final int STATUS_CODE = HttpServletResponse.SC_UNAUTHORIZED;
}
