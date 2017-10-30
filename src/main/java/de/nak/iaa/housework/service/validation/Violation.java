package de.nak.iaa.housework.service.validation;

public class Violation {

	private final String message;
	
	public Violation(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
