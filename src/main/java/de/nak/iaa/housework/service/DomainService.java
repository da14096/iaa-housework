package de.nak.iaa.housework.service;

import java.util.Collection;

public interface DomainService <RESPTYPE> {

	Collection <RESPTYPE> readAll ();
	
	RESPTYPE update(RESPTYPE item);
	
	void delete (RESPTYPE item);
}
