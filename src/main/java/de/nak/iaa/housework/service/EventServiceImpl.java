package de.nak.iaa.housework.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;
import de.nak.iaa.housework.service.validation.ValidationException;
import de.nak.iaa.housework.service.validation.ValidationService;
import de.nak.iaa.housework.service.validation.Violation;

/**
 * Die Implementierung eines {@link EventService}
 * 
 * @author da0015 14096
 */
@Service
public class EventServiceImpl extends AbstractDomainService<Event> implements EventService {

	@Autowired
	protected EventServiceImpl(DomainRepository repository, ValidationService service) {
		super(repository, service, Event.class);
	}
	
	@Override
	public Event persist(Event item) throws ValidationException {
		if (item.getType() == EventType.EXAMN) {
			item.setChangeDuration(30);
		}
		return super.persist(item);
	}
	@Override
	@Transactional
	public void delete(Event item) {
		PropertyFilter studentsClassesFilter = 
				new PropertyFilter(item, Operator.MEMBER, StudentsClass.PROPERTY_NAME_EVENTS_TO_ATTEND);
		PropertyFilterChain chain = PropertyFilterChain.startWith(studentsClassesFilter);
		repository.readAll(StudentsClass.class, chain).forEach(clazz -> {
			clazz.cancelEvent(item);
			repository.update(clazz);
		});;
		super.delete(item);
	}
	
	@Override
	@Transactional(rollbackFor=ValidationException.class)
	public List<Event> saveEvent(Event event, int weeks, boolean validate) throws ValidationException {
		if (event.getId() != null) {
			return Arrays.asList(update(event, validate));
		} else {
			List <Violation> allViolations = new ArrayList<>();
			List <Event> allEvents = new ArrayList<>();
			for (int i = 0; i <= weeks; i++) {
				Event clone = new Event(event.getType(), event.getTitle(), event.getStart().plus(i, ChronoUnit.WEEKS), 
										event.getEnd().plus(i, ChronoUnit.WEEKS), event.getRooms(), 
										event.getLecturer(), event.getChangeDuration());
				try {
					allEvents.add(persist(clone, validate));
				} catch (ValidationException e) {
					allViolations.addAll(e.getViolations());
				}
			}
			if (!allViolations.isEmpty()) {
				throw new ValidationException(allViolations);
			}
			return allEvents;
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<StudentsClass> getAssignedStudentsClasses(Event event) {
		if (event.getId() == null) {
			return Collections.emptyList();
		}
		PropertyFilter filter = new PropertyFilter(event, Operator.MEMBER, StudentsClass.PROPERTY_NAME_EVENTS_TO_ATTEND);
		PropertyFilterChain chain = PropertyFilterChain.startWith(filter);
		return repository.readAll(StudentsClass.class, chain);
	}
	@Override
	@Transactional(readOnly=true)
	public Map<LocalDate, List<Event>> getEventsForLecturer(Lecturer lecturer, LocalDate start, LocalDate end) {
		PropertyFilter lecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		return getAllEvents(start, end, lecturerFilter);
	}
	@Override
	public Map<LocalDate, List<Event>> getEventsForRoom(Room room, LocalDate start, LocalDate end) {
		PropertyFilter lecturerFilter = new PropertyFilter(room, Operator.EQ, Event.PROPERTY_NAME_ROOMS);
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
		return FilterUtils.mapEvents(allEventsInInterval);
	}
	
	@Override
	protected Object extractDatabaseID(Event entity) {
		return entity.getId();
	}
}
