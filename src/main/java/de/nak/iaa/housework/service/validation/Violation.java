package de.nak.iaa.housework.service.validation;

import java.io.Serializable;

public class Violation implements Serializable {

	private static final long serialVersionUID = -4490906054457238211L;

	private final String message;
	
	public Violation(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
