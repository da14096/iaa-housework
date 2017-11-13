package de.nak.iaa.housework.service.validation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Validiert f�r ein gegebenes Objekt, dass die mittels {@link #propertiesToValidate} �bergebenen Attribute
 * nicht null sind. Hierzu wird eine Map �bergeben, welche einen Getter f�r ein Benanntes Property bereitstellt.
 * 
 * @author da0015 14096
 * @param <TYPE> der zu validierende Typ
 */
public class PropertyNotNullValidator <TYPE> extends TypeOrientedValidator<TYPE> {

	private final Map <String, Function<TYPE, ?>> propertiesToValidate;
	
	public PropertyNotNullValidator(Class <TYPE> type, Map <String, Function<TYPE, ?>> propertiesToValidate) {
		super (type);
		this.propertiesToValidate = propertiesToValidate;
	}
	
	@Override
	public List<Violation> validate(TYPE entity) {
		return propertiesToValidate
				.keySet()
				.stream()
				.map(propertyName -> validate (entity, propertyName))
				.filter(item -> item != null)
				.collect(Collectors.toList());
	}
	protected Violation validate (TYPE entity, String propertyName) {
		if (propertiesToValidate.get(propertyName).apply(entity) == null) {
			return createViolationForMissingProperty(entity, propertyName);
		} else {
			return null;
		}
	}
	
	protected Violation createViolationForMissingProperty (TYPE object, String propertyName) {
		return new Violation("Die Eigenschaft [" + propertyName + "] am Objekt [" + object + "] darf nicht leer sein.");
	}
}
