package de.nak.iaa.housework.service;

import java.util.List;

public interface DomainService <RESPTYPE> {

	List <RESPTYPE> readAll ();
	
	RESPTYPE persist (RESPTYPE item) throws ValidationException;
	
	RESPTYPE update (RESPTYPE item) throws ValidationException;
	
	void delete (RESPTYPE item);
}
