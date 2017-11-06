package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

@Service
public class EventService extends AbstractDomainService<Event> {

	@Autowired
	protected EventService(DomainRepository repository, ValidationService service) {
		super(repository, service, Event.class);
	}
}
