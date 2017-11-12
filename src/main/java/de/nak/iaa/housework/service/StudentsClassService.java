package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;

public interface StudentsClassService extends DomainService<StudentsClass> {

	List <Event> addEvent (StudentsClass clazz, Event event, boolean validate, int weeks) throws ValidationException;
	
	StudentsClass removeEvent (StudentsClass clazz, Event event) throws ValidationException;
	
	Map <LocalDate, List<Event>> resolveEventsMapped(StudentsClass clazz, LocalDate start, LocalDate end);
	
}
