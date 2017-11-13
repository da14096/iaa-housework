package de.nak.iaa.housework.service.validation;

/**
 * Abstrakte Implementierung eins Typ-orientierten Validators
 * 
 * @author da0015 14096
 * @param <TYPE> der Typ
 */
abstract class TypeOrientedValidator <TYPE> implements Validator <TYPE> {

	private final Class <TYPE> respType;
	
	public TypeOrientedValidator(Class <TYPE> respType) {
		this.respType = respType;
	}
	@Override
	public final boolean isResponsible(Class<?> type) {
		return respType.isAssignableFrom(type);
	}
}
