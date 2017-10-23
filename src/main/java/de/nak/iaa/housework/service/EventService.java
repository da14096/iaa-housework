package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.repository.DomainRepository;

@Service
public class EventService extends GenericDomainService<Event> {

	@Autowired
	protected EventService(DomainRepository repository) {
		super(repository, Event.class);
	}
}
