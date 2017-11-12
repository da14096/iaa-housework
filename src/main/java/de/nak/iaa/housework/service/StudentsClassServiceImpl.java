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
	public void addEvent(final StudentsClass clazz, Event event, boolean validate) throws ValidationException {
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		persistentStudentsClass.addEvent(event);
		if (validate) {
			validate(persistentStudentsClass);
		}
		repository.update(persistentStudentsClass);
	}
	@Override
	@Transactional
	public void removeEvent(final StudentsClass clazz, Event event) throws ValidationException {
		StudentsClass persistentStudentsClass = repository.find(StudentsClass.class, clazz.getId());
		persistentStudentsClass.cancelEvent(event);
		repository.update(persistentStudentsClass);
	}
}
