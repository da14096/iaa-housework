package de.nak.iaa.housework.service;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = -7481044062102871761L;

	public AlreadyExistsException () {
		
	}
	
	public AlreadyExistsException (String message) {
		super (message);
	}
}
