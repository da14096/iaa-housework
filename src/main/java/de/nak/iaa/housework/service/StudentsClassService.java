package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Spezialisierung des allgemeinen {@link DomainService}. Angereichtert um Zenturien-spezifische Methoden.
 * 
 * @author da0015 14096
 *
 */
public interface StudentsClassService extends DomainService<StudentsClass> {

	/**
	 * Fügt einer Zenturie ein Ereignis hinzu und speichert dies. Über die Schalter validate und weeks
	 * kann festgelegt werden, ob validiert werden soll und die das Ereignis über die definierte Anzahl Wochen
	 * wiederholt werden soll. ACHTUNG: Das erstellen von wiederholten Ereignissen ist nur bei noch nicht persistierten
	 * Ereignissen möglich. Ist das übergebene Ereignis bereits persistent, hat der Schalter keine Wirkung.
	 * 
	 * @param clazz die Zenturie, der die Ereignisse hinzugefügt werden sollen.
	 * @param event das Ereignis das hinzugefügt werden soll
	 * @param validate soll validiert werden
	 * @param weeks wiederholung über Wchen
	 * @return die hinzugefügten Ereignisse (im Falle von WIederholungen mehrere, sonst eins)
	 * @throws ValidationException falls Validierungen fehlschlagen.
	 */
	List <Event> addEvent (StudentsClass clazz, Event event, boolean validate, int weeks) throws ValidationException;
	
	/**
	 * Löscht von einer Zenturie ein Ereignis hinzu und speichert dies. 
	 * 
	 * @param clazz die Zenturie, der die Ereignisse hinzugefügt werden sollen.
	 * @param event das Ereignis das hinzugefügt werden soll
	 * @return die aktualisierte Zenturie
	 * @throws ValidationException falls Validierungen fehlschlagen.
	 */
	StudentsClass removeEvent (StudentsClass clazz, Event event) throws ValidationException;
	
	/**
	 * Diese Methode liest nachträglich die Ereignisse einer Zenturie in einem spezifizierten Zeitraum und mapped die
	 * gesammelten Ereignisse nach ihren Datümern.
	 * 
	 * @param clazz die Zenturie
	 * @param start start des Intervalls
	 * @param end ende des Intervalls
	 * @return die gemappten Ereignisse
	 */
	Map <LocalDate, List<Event>> resolveEventsMapped(StudentsClass clazz, LocalDate start, LocalDate end);
	
}
