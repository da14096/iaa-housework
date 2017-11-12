package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;
import de.nak.iaa.housework.service.validation.ValidationService;
import de.nak.iaa.housework.service.validation.Violation;

@Service
public class EventServiceImpl extends AbstractDomainService<Event> implements EventService {

	@Autowired
	protected EventServiceImpl(DomainRepository repository, ValidationService service) {
		super(repository, service, Event.class);
	}
	
	@Override
	@Transactional(rollbackFor=ValidationException.class)
	public List<Event> persistRepeated(Event event, int weeks, boolean force) throws ValidationException {
		List <Violation> allViolations = new ArrayList<>();
		List <Event> allEvents = new ArrayList<>();
		for (int i = 0; i <= weeks; i++) {
			Event clone = new Event(event.getType(), event.getTitle(), event.getStart().plus(i, ChronoUnit.WEEKS), 
									event.getEnd().plus(i, ChronoUnit.WEEKS), event.getRoom(), 
									event.getLecturer(), event.getChangeDuration());
			try {
				allEvents.add(persist(clone, force));
			} catch (ValidationException e) {
				allViolations.addAll(e.getViolations());
			}
		}
		if (!allViolations.isEmpty()) {
			throw new ValidationException(allViolations);
		}
		return allEvents;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<LocalDate, List<Event>> getEventsForStudentsClass(StudentsClass clazz, LocalDate start, LocalDate end) {
//		lazily fetch the events from the database
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		List <Event> allEvents = persistentStudentsClass.getEventsToAttend();
		List <Event> requestedEvents = allEvents.parallelStream()
					.filter(event -> event.getStart().isAfter(start.atStartOfDay()) && 
										event.getStart().isBefore(end.plus(1, ChronoUnit.DAYS).atStartOfDay()))
					.collect(Collectors.toList());
		return mapEvents(requestedEvents);
	}
	@Override
	@Transactional(readOnly=true)
	public Map<LocalDate, List<Event>> getEventsForLecturer(Lecturer lecturer, LocalDate start, LocalDate end) {
		PropertyFilter lecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		return getAllEvents(start, end, lecturerFilter);
	}
	@Override
	public Map<LocalDate, List<Event>> getEventsForRoom(Room room, LocalDate start, LocalDate end) {
		PropertyFilter lecturerFilter = new PropertyFilter(room, Operator.EQ, Event.PROPERTY_NAME_ROOM);
		return getAllEvents(start, end, lecturerFilter);
	}
	
	private Map <LocalDate, List <Event>> getAllEvents (LocalDate start, LocalDate end, PropertyFilter customFilter) {
		PropertyFilter startFilter = new PropertyFilter(start.atStartOfDay(), 
														Operator.LESSEQ, 
														Event.PROPERTY_NAME_START);
		PropertyFilter endFilter = new PropertyFilter(end.plus(1, ChronoUnit.DAYS).atStartOfDay(), 
														Operator.GREATEREQ, 
														Event.PROPERTY_NAME_START);
		
		
		PropertyFilterChain chain = PropertyFilterChain.startWith(startFilter)
														.appendFilter(endFilter, Connector.AND)
														.appendFilter(customFilter, Connector.AND);
		
		List <Event> allEventsInInterval = repository.readAll(Event.class, chain);
		return mapEvents(allEventsInInterval);
	}

	private Map<LocalDate, List<Event>> mapEvents(List<Event> allEventsInInterval) {
		Map <LocalDate, List<Event>> result = new HashMap<>();
		allEventsInInterval.forEach(event -> {
			List <Event> group = result.get(event.getStart().toLocalDate());
			if (group == null) {
				group = new ArrayList<>();
				result.put(event.getStart().toLocalDate(), group);
			}
			group.add(event);
		});
		return result;
	}
}
