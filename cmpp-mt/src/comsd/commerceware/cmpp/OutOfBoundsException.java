// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:27:48
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   OutOfBoundsException.java
package comsd.commerceware.cmpp;

public class OutOfBoundsException extends Exception {

	public OutOfBoundsException() {
		details = "array length or data type is invalid";
	}

	String details;
}