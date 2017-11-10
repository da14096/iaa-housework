package de.nak.iaa.housework.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

@Service
public class StudentsClassServiceImpl extends AbstractDomainService<StudentsClass> implements StudentsClassService {

	@Autowired
	protected StudentsClassServiceImpl(DomainRepository repository, ValidationService service) {
		super(repository, service, StudentsClass.class);
	}
	
	@Override
	@Transactional
	public void addEvent(final StudentsClass clazz, Event event) {
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		persistentStudentsClass.addEvent(event);
		repository.update(persistentStudentsClass);
	}
}
