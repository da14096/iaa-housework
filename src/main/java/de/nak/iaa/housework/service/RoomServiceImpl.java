package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
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
			FilterUtils.instance(repository)
						.getAllOverlappingEvents(start, end)
						.stream()
						.map(event -> event.getRoom())
						.forEach(event -> allRooms.remove(event));
		}
		return allRooms;
	}
}
