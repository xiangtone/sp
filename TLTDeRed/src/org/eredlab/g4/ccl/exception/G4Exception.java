package org.eredlab.g4.ccl.exception;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * 公共异常类<br>
 * 
 */
public class G4Exception extends RuntimeException {

	public G4Exception() {
		super();
	}

	public G4Exception(String msg) {
		super(GlobalConstants.Exception_Head + msg);
	}
}
