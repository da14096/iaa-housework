package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

/**
 * Die Implementierung eines {@link RoomService}
 * 
 * @author da0015 14096
 */
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
			FilterUtils.instance(repository)
						.getAllOverlappingEvents(start, end)
						.stream()
						.map(event -> event.getRooms())
						.collect(() -> new ArrayList<>(), (l1, l2) -> l1.addAll(l2), (l1, l2) -> l1.addAll(l2))
						.forEach(room -> allRooms.remove(room));
		}
		return allRooms;
	}
	@Override
	protected Object extractDatabaseID(Room entity) {
		return entity.getName();
	}
}
