package com.cooksys.twitter.exception;

import javax.servlet.http.HttpServletResponse;

public class UserAlreadyExistsException {

	public final static int STATUS_CODE = HttpServletResponse.SC_CONFLICT;
}
