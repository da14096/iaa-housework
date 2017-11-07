package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;

@ValidatorBean(previentValidator = EventPropertyValidator.class)
public class RoomChangeTimeValidator extends TypeOrientedValidator<Event> {

	private final DomainRepository repository;
	
	@Autowired
	public RoomChangeTimeValidator(DomainRepository repository) {
		super(Event.class);
		this.repository = repository;
	}
	
	@Override
	public List<Violation> validate(Event entity) {
		Room room = entity.getRoom();
		int changeTime = room.getChangeDuration();
		
		LocalDateTime maxEndPreviousEvent = entity.getStart().minus(changeTime, ChronoUnit.MINUTES);
		PropertyFilter startFilter = new PropertyFilter(Operator.LESSEQ, Event.PROPERTY_NAME_END, entity.getStart()); 
		PropertyFilter roomFilter = new PropertyFilter(Operator.EQ, Event.PROPERTY_NAME_ROOM, room);
		PropertyFilter previousEndEventFilter = new PropertyFilter(Operator.GREATEREQ, 
																	Event.PROPERTY_NAME_END, 
																	maxEndPreviousEvent);
		
		PropertyFilterChain filter = PropertyFilterChain.startWith(startFilter)
															.appendFilter(previousEndEventFilter, Connector.AND)
															.appendFilter(roomFilter, Connector.AND);
		
		
		List <Event> matchingEvents = repository.readAll(Event.class, filter);
		List <Violation> violations = new ArrayList<>();
		if (!matchingEvents.isEmpty()) {
			violations.add(new Violation("Bitte Wechselzeit des Raumes [" + room + "] von " + changeTime + 
					" Minuten beachten. Der frühestmögliche Beginn der Veranstaltung [" + entity + "] ist um " +
					maxEndPreviousEvent));
		}
		return violations;
	}
}
