package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.repository.DomainRepository;

@Service
public class LecturerService extends AbstractDomainService<Lecturer> {

	@Autowired
	protected LecturerService(DomainRepository repository) {
		super(repository, Lecturer.class);
	}
	
	@Override
	protected Object getIdFromItem(Lecturer item) {
		return item.getPersonnelNumber();
	}
}
