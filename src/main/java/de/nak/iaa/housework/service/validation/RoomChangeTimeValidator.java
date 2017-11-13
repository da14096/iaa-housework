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
		List <Violation> violations = new ArrayList<>();
		for (Room room: entity.getRooms()) {
			LocalDateTime maxEndPreviousEvent = entity.getStart().minus(room.getChangeDuration(), ChronoUnit.MINUTES);
			PropertyFilter previousEndEventFilter = new PropertyFilter(maxEndPreviousEvent, 
																		Operator.LESSEQ, 
																		Event.PROPERTY_NAME_END);
			PropertyFilter startFilter = new PropertyFilter(entity.getStart(), Operator.GREATEREQ, Event.PROPERTY_NAME_END);
			PropertyFilterChain filter = PropertyFilterChain.startWith(startFilter)
										.appendFilter(previousEndEventFilter, Connector.AND);
			
//			unfortunately we have to filter for the room at this point again because h2-database does not support
//			MEMBER-Filter on Composed Keys.
			boolean violation = repository.readAll(Event.class, filter)
								.stream().map(event -> event.getRooms())
								.collect(() -> new ArrayList<>(), (l1, l2) -> l1.addAll(l2), (l1, l2) -> l1.addAll(l2))
								.stream()
								.anyMatch(room::equals);
			if (violation) {
				violations.add(new Violation("Bitte die Wechselzeit des Raumes [" + room + "] von " + room.getChangeDuration() + 
						" Minuten beachten. Der frühestmögliche Beginn der Veranstaltung [" + entity + "] ist um " +
						maxEndPreviousEvent));
			}
		}
		return violations;
	}
}
