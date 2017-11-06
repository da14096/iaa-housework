package de.nak.iaa.housework.service;

import java.util.List;

import de.nak.iaa.housework.model.repository.PropertyFilterChain;

public interface DomainService <RESPTYPE> {

	List <RESPTYPE> readAll ();
	
	List <RESPTYPE> readAll (PropertyFilterChain filterChain);
	
	RESPTYPE persist (RESPTYPE item) throws ValidationException;
	
	RESPTYPE update (RESPTYPE item) throws ValidationException;
	
	void delete (RESPTYPE item);
}
