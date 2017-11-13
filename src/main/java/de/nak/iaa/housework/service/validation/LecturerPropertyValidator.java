package de.nak.iaa.housework.service.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.nak.iaa.housework.model.Lecturer;

/**
 * Dieser Validator validiert die Konsistenz einer Dozenten-Entität.
 * 
 * @author David Aldrup 14096
 */
@ValidatorBean
public class LecturerPropertyValidator extends PropertyNotNullValidator<Lecturer> {

	public LecturerPropertyValidator() {
		super(Lecturer.class, getPropertiesToValidate());
	}
	
	private static Map<String, Function<Lecturer, ?>> getPropertiesToValidate() {
		Map <String, Function<Lecturer, ?>> result = new HashMap<>();
		result.put(Lecturer.PROPERTY_NAME, Lecturer::getName);
		result.put(Lecturer.PROPERTY_SURNAME, Lecturer::getSurname);
		result.put(Lecturer.PROPERTY_MINIMAL_BREAK_TIME, Lecturer::getMinimalBreakTime);
		return result;
	}
}
