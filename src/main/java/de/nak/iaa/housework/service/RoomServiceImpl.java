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
		PropertyFilterChain chain = PropertyFilterChain.emptyChain();
		if (start!= null) {
			PropertyFilter startFilter = new PropertyFilter(Operator.GREATEREQ, 
															Event.PROPERTY_NAME_START, 
															start);
			chain.appendFilter(startFilter, Connector.AND);
		}
		if (end != null) {
			PropertyFilter endFilter = new PropertyFilter(Operator.LESSEQ, 
															Event.PROPERTY_NAME_END, 
															end);
			chain.appendFilter(endFilter, Connector.AND);
		}
		if (!chain.hasFilters()) {
			repository.readAll(Event.class, chain)
						.stream()
						.map(event -> event.getRoom())
						.forEach(blockedRoom -> allRooms.remove(blockedRoom));
		}  
		return allRooms;
	}
}
