package de.nak.iaa.housework.service;

import java.util.Collection;

import de.nak.iaa.housework.model.repository.PropertyFilterWrapper;

public interface DomainService <RESPTYPE> {

	Collection <RESPTYPE> readAll (PropertyFilterWrapper... filter);
	
	RESPTYPE persist (RESPTYPE item) throws ValidationException;
	
	RESPTYPE update (RESPTYPE item) throws ValidationException;
	
	void delete (RESPTYPE item);
}
