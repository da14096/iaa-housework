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
		
		Lecturer lecturer = entity.getLecturer();
		Room room = entity.getRoom();
		
		PropertyFilter notTheEventToValidate = new PropertyFilter(entity, Operator.NOTEQ);
		
		PropertyFilter sameRoomFilter = new PropertyFilter(room, Operator.EQ, Event.PROPERTY_NAME_ROOM);	
		Set <Event> eventsWithSameRoom = FilterUtils.instance(repository)
											.getAllOverlappingEvents(start, end, sameRoomFilter, notTheEventToValidate);
		
		List <Violation> violations = new ArrayList<>();
		if (!eventsWithSameRoom.isEmpty()) {
			violations.add(new Violation("Räume können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Raum ["
					+ room + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits belegt."));
		}
		
		PropertyFilter sameLecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		Set <Event> eventsWithSameLecturer = FilterUtils.instance(repository)
										.getAllOverlappingEvents(start, end, sameLecturerFilter, notTheEventToValidate);
		if (!eventsWithSameLecturer.isEmpty()) {
			violations.add(new Violation("Dozenten können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Dozent ["
					+ lecturer + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits zugeordnet."));
		}
		return violations;
	}
}
