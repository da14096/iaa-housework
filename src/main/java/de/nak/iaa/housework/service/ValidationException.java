package de.nak.iaa.housework.service;

import java.util.List;

import de.nak.iaa.housework.service.validation.Violation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -1430014590123340078L;

	private final List <Violation> violations;
	
	public ValidationException (List <Violation> violations) {
		this.violations = violations;
	}
	
	public List<Violation> getViolations() {
		return violations;
	}
}
