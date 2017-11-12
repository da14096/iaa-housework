package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;

public interface EventService extends DomainService<Event> {

	List <Event> persistRepeated (Event event, int weeks, boolean force) throws ValidationException;
	
	Map <LocalDate, List<Event>> getEventsForRoom (Room room, LocalDate start, LocalDate end);
	
	Map <LocalDate, List<Event>> getEventsForLecturer (Lecturer lecturer, LocalDate start, LocalDate end);
	
	Map <LocalDate, List<Event>> getEventsForStudentsClass(StudentsClass clazz, LocalDate start, LocalDate end);
}
