package de.nak.iaa.housework.service.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.nak.iaa.housework.model.StudentsClass;

/**
 * Dieser Validator validiert die Konsistenz einer Zenturien-Entität.
 * 
 * @author David Aldrup 14096
 */
@ValidatorBean
public class StudentsClassPropertyValidator extends PropertyNotNullValidator<StudentsClass> {

	public StudentsClassPropertyValidator() {
		super(StudentsClass.class, getPropertiesToValidate());
	}
	
	private static Map<String, Function<StudentsClass, ?>> getPropertiesToValidate() {
		Map <String, Function<StudentsClass, ?>> result = new HashMap<>();
		result.put(StudentsClass.PROPERTY_ID_FIELD_OF_STUDY, StudentsClass::getFieldOfStudy);
		result.put(StudentsClass.PROPERTY_ID_YEAR, StudentsClass::getYear);
		result.put(StudentsClass.PROPERTY_ID_FORM, StudentsClass::getForm);
		result.put(StudentsClass.PROPERTY_MINIMAL_BREAK_TIME, StudentsClass::getMinimalBreakTime);
		result.put(StudentsClass.PROPERTY_SIZE, StudentsClass::getSize);
		return result;
	}
}
