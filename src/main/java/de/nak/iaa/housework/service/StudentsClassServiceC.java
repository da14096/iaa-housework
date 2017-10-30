package de.nak.iaa.housework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationService;

@Service
public class StudentsClassServiceC extends AbstractDomainService<StudentsClass> {

	@Autowired
	protected StudentsClassServiceC(DomainRepository repository, ValidationService service) {
		super(repository, service, StudentsClass.class);
	}
	
	@Override
	protected Object getIdFromItem(StudentsClass item) {
		return item.getId();
	}
}
