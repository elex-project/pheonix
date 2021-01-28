/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutOfCalendarDataException extends Exception {
	private static final long serialVersionUID = 6437829835039347888L;

	static final byte UNDER_MINIMUM = -1;
	static final byte OVER_MAXIMUM = 1;

	private byte errCode;

	OutOfCalendarDataException(byte err){
		errCode = err;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String toString() {
		return "OutOfCalendarDataException: "+super.toString();
	}

}
