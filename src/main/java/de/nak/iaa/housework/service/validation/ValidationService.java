package de.nak.iaa.housework.service.validation;

import java.util.Set;

/**
 * Service welcher Funktionen zum Durchf�hren von jeglichen Validierungen (fachlich/ technisch) f�r Entit�ten 
 * bereitstellt.
 * 
 * @author da0015 14096
 */
public interface ValidationService {
	
	/**
	 * Validiert eine Entit�t. Gibt eine Liste mit m�glichen fehlgeschlagenen Validierungen zur�ck. 
	 * Eine leere Liste bedeutet, dass das Objekt valide ist.
	 * 
	 * @param entity die Entit�t
	 * @return die Liste mit fehlgeschlagenen Validierungen
	 */
	<ET> Set <Violation> validate (ET entity);

}
