package de.nak.iaa.housework.service;

import java.util.List;

import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Standardschnittstelle für alle Business-Objekte. Aufrufe an die Datenhaltungsschicht und Validierungen.
 * 
 * @author da0015 14096
 * @param <RESPTYPE> der Typ für den dieser Service zuständig ist.
 */
public interface DomainService <RESPTYPE> {

	/**
	 * @return alle persistenten Objekte des verantwortlichen Typs
	 */
	List <RESPTYPE> readAll ();
	
	/**
	 * Persistiert ein Objekt (Create). Über den Schalter validate kan festgelegt werden, ob eine Validierung stattfinden
	 * soll oder nicht. 
	 * @param item das Element
	 * @param validate soll eine Validierung stattfinden?
	 * @return das Objekte mit ID(bei Datenbankseitig vergebenen IDs)
	 * @throws ValidationException für den Fall dass validiert wird und Fehler auftreten.
	 */
	RESPTYPE persist (RESPTYPE item, boolean validate) throws ValidationException;
	
	/**
	 * @see {@link #persist(Object, boolean)}
	 * selbes Verhalten zu {@link #persist(Object, boolean)} mit Validierung
	 */
	RESPTYPE persist (RESPTYPE item) throws ValidationException;
	
	/**
	 * Aktualisiert ein Objekt (Update). Über den Schalter validate kan festgelegt werden, ob eine Validierung stattfinden
	 * soll oder nicht. 
	 * @param item das Element
	 * @param validate soll eine Validierung stattfinden?
	 * @return das Objekte mit ID(bei Datenbankseitig vergebenen IDs)
	 * @throws ValidationException für den Fall dass validiert wird und Fehler auftreten.
	 */
	RESPTYPE update (RESPTYPE item, boolean validate) throws ValidationException;
	/**
	 * @see {@link #update(Object, boolean)}
	 * selbes Verhalten zu {@link #update(Object, boolean)} mit Validierung
	 */
	RESPTYPE update (RESPTYPE item) throws ValidationException;
 	
	/**
	 * Löscht ein Objekt.
	 * @param item das Objekt
	 */
	void delete (RESPTYPE item);
}
