package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;

@Service
public class RoomService extends AbstractDomainService<Room> {
	
	@Autowired
	protected RoomService(DomainRepository repository) {
		super(repository, Room.class);
	}
	
	@Override
	protected Object getIdFromItem(Room item) {
		return item.getName();
	}
}
