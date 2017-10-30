package de.nak.iaa.housework.service.validation;

import java.util.List;

public interface Validator <RESPTYPE> {

	boolean isResponsible (Class<?> type);
	
	List <Violation> validate (RESPTYPE entity);
}
