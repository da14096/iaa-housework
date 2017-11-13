package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationException;
import de.nak.iaa.housework.service.validation.ValidationService;

/**
 * Die Implementierung eines {@link StudentsClassService}
 * 
 * @author da0015 14096
 */
@Service
public class StudentsClassServiceImpl extends AbstractDomainService<StudentsClass> implements StudentsClassService {

	private final EventService eventService;
	
	@Autowired
	protected StudentsClassServiceImpl(DomainRepository repository, ValidationService service, EventService eventService) {
		super(repository, service, StudentsClass.class);
		this.eventService = eventService;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<LocalDate, List<Event>> resolveEventsMapped(StudentsClass clazz, LocalDate start, LocalDate end) {
//		lazily fetch the events from the database
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		Set <Event> allEvents = persistentStudentsClass.getEventsToAttend();
		List <Event> requestedEvents = allEvents.parallelStream()
					.filter(event -> event.getStart().isAfter(start.atStartOfDay()) && 
										event.getStart().isBefore(end.plus(1, ChronoUnit.DAYS).atStartOfDay()))
					.collect(Collectors.toList());
		return FilterUtils.mapEvents(requestedEvents);
	}
	
	@Override
	@Transactional(rollbackFor=ValidationException.class)
	public List <Event> addEvent(final StudentsClass clazz, Event event, boolean validate, int weeks) throws ValidationException {
		List <Event> eventsToAdd;
		if (event.getId() == null) {
			eventsToAdd = eventService.saveEvent(event, weeks, validate);
		} else {
			eventsToAdd = Arrays.asList(eventService.update(event, validate));
		}
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		persistentStudentsClass.addEvents(eventsToAdd);
		if (validate) {
			validate(persistentStudentsClass);
		}
		repository.update(persistentStudentsClass);
		return eventsToAdd;
	}
	@Override
	@Transactional
	public StudentsClass removeEvent(final StudentsClass clazz, Event event) throws ValidationException {
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		persistentStudentsClass.cancelEvent(event);
		return repository.update(persistentStudentsClass);
	}
	@Override
	protected Object extractDatabaseID(StudentsClass entity) {
		return entity.getId();
	}
}
