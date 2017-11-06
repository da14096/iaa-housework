package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;

@ValidatorBean(previentValidator = EventReferencesValidator.class)
public class LecturerBreakTimeValidator extends TypeOrientedValidator<Event> {

	private final DomainRepository repository;
	
	@Autowired
	public LecturerBreakTimeValidator(DomainRepository repository) {
		super(Event.class);
		this.repository = repository;
	}
	
	@Override
	public List<Violation> validate(Event entity) {
		Lecturer lecturer = entity.getLecturer();
		int breakTime = lecturer.getMinimalBreakTime();
		
		LocalDateTime maxEndPreviousEvent = entity.getStart().minus(breakTime, ChronoUnit.MINUTES);
		PropertyFilter startFilter = new PropertyFilter(Operator.LESSEQ, Event.PROPERTY_NAME_END, entity.getStart()); 
		PropertyFilter lecturerFilter = new PropertyFilter(Operator.EQ, Event.PROPERTY_NAME_LECTURER, lecturer);
		PropertyFilter previousEndEventFilter = new PropertyFilter(Operator.GREATEREQ, 
																	Event.PROPERTY_NAME_END, 
																	maxEndPreviousEvent);
		
		PropertyFilterChain filter = PropertyFilterChain.startWith(startFilter)
															.appendFilter(previousEndEventFilter, Connector.AND)
															.appendFilter(lecturerFilter, Connector.AND);
		
		
		List <Event> matchingEvents = repository.readAll(Event.class, filter);
		List <Violation> violations = new ArrayList<>();
		if (!matchingEvents.isEmpty()) {
			violations.add(new Violation("Bitte Pausenzeiten des Dozenten [" + lecturer + "] von " + breakTime + 
					" Minuten beachten. Der frühestmögliche Beginn der Veranstaltung [" + entity + "] ist um " +
					maxEndPreviousEvent));
		}
		
		PropertyFilter roomFilter = new PropertyFilter(Operator.EQ, Event.PROPERTY_NAME_ROOM, entity.getRoom());
		filter = PropertyFilterChain.startWith(startFilter)
									.appendFilter(previousEndEventFilter, Connector.AND)
									.appendFilter(roomFilter, Connector.AND);
		matchingEvents = repository.readAll(Event.class, filter);
		if (!matchingEvents.isEmpty()) {
			violations.add(new Violation("Bitte die Wechselzeit des Raumes [" + entity.getRoom() + "] von " + breakTime + 
					" Minuten beachten. Der frühestmögliche Beginn der Veranstaltung [" + entity + "] ist um " +
					maxEndPreviousEvent));
		}
		return violations;
	}

}
