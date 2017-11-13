package de.nak.iaa.housework.service;

import java.util.Arrays;
import java.util.Collection;

import de.nak.iaa.housework.service.validation.Violation;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -1430014590123340078L;

	private final Collection <Violation> violations;
	
	public ValidationException (Collection <Violation> violations) {
		this.violations = violations;
	}
	public ValidationException (Violation violation) {
		this.violations = Arrays.asList(violation);
	}
	
	public Collection<Violation> getViolations() {
		return violations;
	}
}
