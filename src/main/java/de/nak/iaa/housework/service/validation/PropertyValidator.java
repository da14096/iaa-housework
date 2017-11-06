package de.nak.iaa.housework.service.validation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PropertyValidator <TYPE> extends TypeOrientedValidator<TYPE> {

	private final Map <String, Function<TYPE, ?>> propertiesToValidate;
	
	public PropertyValidator(Class <TYPE> type, Map <String, Function<TYPE, ?>> propertiesToValidate) {
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
