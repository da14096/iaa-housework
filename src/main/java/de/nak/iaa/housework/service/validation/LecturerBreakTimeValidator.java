package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;

/**
 * Validiert, dass die Pausenzeit eines Dozenten eingehalten wird.
 *  
 * @author da0015 14096
 */
@ValidatorBean(previentValidator = EventPropertyValidator.class)
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
		PropertyFilter lecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		PropertyFilter startFilter = new PropertyFilter(entity.getStart(), Operator.GREATEREQ, Event.PROPERTY_NAME_END);
		PropertyFilter previousEndEventFilter = new PropertyFilter(maxEndPreviousEvent, 
																	Operator.LESSEQ, 
																	Event.PROPERTY_NAME_END);
		
		PropertyFilterChain filter = PropertyFilterChain.startWith(startFilter)
															.appendFilter(previousEndEventFilter, Connector.AND)
															.appendFilter(lecturerFilter, Connector.AND);
		
		List <Event> matchingEvents = repository.readAll(Event.class, filter);
		return !matchingEvents.isEmpty()?
			Arrays.asList(new Violation("Bitte Pausenzeiten des Dozenten [" + lecturer + "] von " + breakTime + 
					" Minuten beachten. Der frühestmögliche Beginn der Veranstaltung [" + entity + "] ist um " +
					maxEndPreviousEvent)):
			Collections.emptyList();
	}

}
