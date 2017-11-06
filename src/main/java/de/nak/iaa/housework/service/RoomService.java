package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

@Service
public class RoomService extends AbstractDomainService<Room> {
	
	@Autowired
	protected RoomService(DomainRepository repository, ValidationService service) {
		super(repository, service, Room.class);
	}
}
