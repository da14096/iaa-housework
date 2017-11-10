package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;
import de.nak.iaa.housework.service.validation.ValidationService;

@Service
public class RoomServiceImpl extends AbstractDomainService<Room> implements RoomService {
	
	@Autowired
	protected RoomServiceImpl(DomainRepository repository, ValidationService service) {
		super(repository, service, Room.class);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Room> getAvailableRooms(LocalDateTime start, LocalDateTime end) {
		List <Room> allRooms = this.readAll();
		
		if (start != null && end != null) {
	//		get the events that start in the interval
			PropertyFilter startGrStartFilter = new PropertyFilter(Operator.GREATEREQ, Event.PROPERTY_NAME_START, start);
			PropertyFilter startLessEndFilter = new PropertyFilter(Operator.LESSEQ, Event.PROPERTY_NAME_START, end);	
			PropertyFilterChain chain = PropertyFilterChain
											.startWith(startGrStartFilter)
											.appendFilter(startLessEndFilter, Connector.AND);
			List <Event> eventsInInterval = repository.readAll(Event.class, chain);
			
//			get the events that end in the interval
			PropertyFilter endGrStartFilter = new PropertyFilter(Operator.GREATEREQ, Event.PROPERTY_NAME_END, start);
			PropertyFilter endLessEndFilter = new PropertyFilter(Operator.LESSEQ, Event.PROPERTY_NAME_END, end);
			chain = PropertyFilterChain.startWith(endGrStartFilter).appendFilter(endLessEndFilter, Connector.AND);
			eventsInInterval.addAll(repository.readAll(Event.class, chain));
			
//			get the events that start before and end after the interval
			PropertyFilter startBefStartFilter = new PropertyFilter(Operator.LESSEQ, Event.PROPERTY_NAME_START, start);
			PropertyFilter endAftEndFilter = new PropertyFilter(Operator.GREATEREQ, Event.PROPERTY_NAME_END, end);
			chain = PropertyFilterChain.startWith(startBefStartFilter).appendFilter(endAftEndFilter, Connector.AND);
			eventsInInterval.addAll(repository.readAll(Event.class, chain));
			
			eventsInInterval.stream().map(event -> event.getRoom()).forEach(event -> allRooms.remove(event));
		}
		return allRooms;
	}
}
