package com.cooksys.twitter.exception;

import javax.servlet.http.HttpServletResponse;

public class TweetDoesNotExistException extends Exception {

	public final static int STATUS_CODE = HttpServletResponse.SC_NOT_FOUND;
	
}
