package de.nak.iaa.housework.service.validation;

import java.util.Set;

/**
 * Service welcher Funktionen zum Durchführen von jeglichen Validierungen (fachlich/ technisch) für Entitäten 
 * bereitstellt.
 * 
 * @author da0015 14096
 */
public interface ValidationService {
	
	/**
	 * Validiert eine Entität. Gibt eine Liste mit möglichen fehlgeschlagenen Validierungen zurück. 
	 * Eine leere Liste bedeutet, dass das Objekt valide ist.
	 * 
	 * @param entity die Entität
	 * @return die Liste mit fehlgeschlagenen Validierungen
	 */
	<ET> Set <Violation> validate (ET entity);

}
