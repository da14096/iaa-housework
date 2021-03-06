package de.nak.iaa.housework.service.validation;

import java.util.List;

/**
 * Schnittstelle eines Validators. Dieser validiert Entitšten eines bestimmten Typs
 * 
 * @author da0015 14096
 *
 * @param <RESPTYPE> der verantwortliche Typ
 */
public interface Validator <RESPTYPE> {

	boolean isResponsible (Class<?> type);
	
	List <Violation> validate (RESPTYPE entity);
}
