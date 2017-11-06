package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;

@ValidatorBean
public class UniqueEventValidator extends TypeOrientedValidator<Event> {

	private DomainRepository repository;

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
		
		PropertyFilter startFilter = new PropertyFilter(Operator.GREATER, Event.PROPERTY_NAME_START, start);
		PropertyFilter endFilter = new PropertyFilter(Operator.LESS, Event.PROPERTY_NAME_END, end);
		PropertyFilter sameRoomFilter = new PropertyFilter(Operator.EQ, Event.PROPERTY_NAME_ROOM, room);
		PropertyFilter sameLecturerFilter = new PropertyFilter(Operator.EQ, Event.PROPERTY_NAME_LECTURER, lecturer);
		
		
		PropertyFilterChain chain = PropertyFilterChain.startWith(startFilter)
														.appendFilter(endFilter, Connector.AND)
														.appendFilter(sameRoomFilter, Connector.AND);
		List <Event> eventsWithSameRoom = repository.readAll(Event.class, chain);
		List <Violation> violations = new ArrayList<>();
		if (!eventsWithSameRoom.isEmpty()) {
			violations.add(new Violation("Räume können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Raum ["
					+ room + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits belegt."));
		}
		
		
		chain = PropertyFilterChain.startWith(startFilter)
									.appendFilter(endFilter, Connector.AND)
									.appendFilter(sameLecturerFilter, Connector.AND);
		List <Event> eventsWithSameLecturer = repository.readAll(Event.class, chain);		
		if (!eventsWithSameLecturer.isEmpty()) {
			violations.add(new Violation("Dozenten können nur einer Veranstaltung zur Zeit zugeordnet sein. Der Dozent ["
					+ lecturer + "] ist in dem Zeitraum vom " + start + " bis " + end + " bereits zugeordnet."));
		}
		return violations;
	}
}
