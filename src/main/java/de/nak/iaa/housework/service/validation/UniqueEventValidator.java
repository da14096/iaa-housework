package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.service.FilterUtils;

/**
 * Validiert, dass ein Dozent und ein Raum jeweils nur "einmalig" besetzt sein dürfen
 * 
 * @author da0015 14096
 */
@ValidatorBean
public class UniqueEventValidator extends TypeOrientedValidator<Event> {

	private final DomainRepository repository;

	@Autowired
	public UniqueEventValidator(DomainRepository repository) {
		super (Event.class);
		this.repository = repository;
	}
	
	@Override
	public List<Violation> validate(Event entity) {
		LocalDateTime start = entity.getStart();
		LocalDateTime end = entity.getEnd();
	
		List <Violation> violations = new ArrayList<>();
		Set <Event> overlappingEvents = FilterUtils.instance(repository).getAllOverlappingEvents(start, end);
		overlappingEvents.remove(entity);
		for (Room room: entity.getRooms()) {
			if (overlappingEvents.stream().anyMatch(event -> event.getRooms().contains(room))) {
				violations.add(new Violation("Räume können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Raum ["
						+ room + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits belegt."));
			}
		}
		
		Lecturer lecturer = entity.getLecturer();
		PropertyFilter sameLecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		Set <Event> eventsWithSameLecturer = FilterUtils.instance(repository)
										.getAllOverlappingEvents(start, end, sameLecturerFilter);
		eventsWithSameLecturer.remove(entity);
		if (!eventsWithSameLecturer.isEmpty()) {
			violations.add(new Violation("Dozenten können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Dozent ["
					+ lecturer + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits zugeordnet."));
		}
		return violations;
	}
}
