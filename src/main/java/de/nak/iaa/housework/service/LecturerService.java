package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

/**
 * Standardimplementierung eines {@link DomainService} für Dozenten
 * 
 * @author da0015 14096
 */
@Service
public class LecturerService extends AbstractDomainService<Lecturer> {

	@Autowired
	protected LecturerService(DomainRepository repository, ValidationService service) {
		super(repository, service, Lecturer.class);
	}
	@Override
	protected Object extractDatabaseID(Lecturer entity) {
		return entity.getPersonnelNumber();
	}
}
