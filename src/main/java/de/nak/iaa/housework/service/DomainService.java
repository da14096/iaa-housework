package de.nak.iaa.housework.service;

import java.util.Collection;

public interface DomainService <RESPTYPE> {

	Collection <RESPTYPE> readAll ();
	
	RESPTYPE persist (RESPTYPE item) throws ValidationException;
	
	RESPTYPE update (RESPTYPE item) throws ValidationException;
	
	void delete (RESPTYPE item);
}
