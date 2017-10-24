package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;

@Service
public class StudentsClassService extends GenericDomainService<StudentsClass> {

	@Autowired
	protected StudentsClassService(DomainRepository repository) {
		super(repository, StudentsClass.class);
	}
}
