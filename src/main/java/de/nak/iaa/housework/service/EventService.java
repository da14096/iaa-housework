package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Spezialisierung des allgemeinen {@link DomainService}. Angereichtert um Veranstaltungs-spezifische Methoden.
 * 
 * @author da0015 14096
 *
 */
public interface EventService extends DomainService<Event> {

	/**
	 * Speichert eine Veranstaltung (entweder erzeugend oder aktualisierend). Über die Schalter weeks kann eine Wiederholung
	 * bei erstmaliger (erzeugender) Speicherung festgelegt werden, validate legt fest ob validiert werden soll.
	 * 
	 * @param event die Veranstaltung
	 * @param weeks Anzahl von Wochen für Wiederholung
	 * @param validate Validierung (ja/nein)
	 * @return die Liste mit Ereignissen(bei Wiederholung mehrere, sonst 1)
	 * @throws ValidationException im Falle von Validierungsfehlern
	 */
	List <Event> saveEvent (Event event, int weeks, boolean validate) throws ValidationException;
	
	/**
	 * Diese Methode liest die Ereignisse eines Raumes in einem spezifizierten Zeitraum und mapped die
	 * gesammelten Ereignisse nach ihren Datümern.
	 * 
	 * @param room der Raum
	 * @param start start des Intervalls
	 * @param end ende des Intervalls
	 * @return die gemappten Ereignisse
	 */
	Map <LocalDate, List<Event>> getEventsForRoom (Room room, LocalDate start, LocalDate end);
	
	/**
	 * Diese Methode liest die Ereignisse eines Dzenten in einem spezifizierten Zeitraum und mapped die
	 * gesammelten Ereignisse nach ihren Datümern.
	 * 
	 * @param lecturer der Dozent
	 * @param start start des Intervalls
	 * @param end ende des Intervalls
	 * @return die gemappten Ereignisse
	 */
	Map <LocalDate, List<Event>> getEventsForLecturer (Lecturer lecturer, LocalDate start, LocalDate end);
	
	/**
	 * Liefert die Zenturien, denen ein Ereignis zugeordnet ist.
	 * 
	 * @param event das Ereignis
	 * @return die Zenturien die daran teilnehmen.
	 */
	List <StudentsClass> getAssignedStudentsClasses (Event event);
}
