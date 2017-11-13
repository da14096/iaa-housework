package de.nak.iaa.housework.service.validation;

import java.util.Arrays;
import java.util.Collection;

/**
 * Exception-Klasse, welche für fehlgeschlagene Validierungen verwendet wird. Enthält mehrere {@link Violation}
 * welche die Informationen beinhalten, welche Validierungen fehlgeschlagen sind.
 * 
 * @author da0015 14096
 *
 */
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
