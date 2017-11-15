package com.cooksys.twitter.exception;

import javax.servlet.http.HttpServletResponse;

public class FollowingException extends Exception {

	public final static int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
}
