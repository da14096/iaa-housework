package de.nak.iaa.housework.service;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilterWrapper;
import de.nak.iaa.housework.service.validation.ValidationService;
import de.nak.iaa.housework.service.validation.Violation;


abstract class AbstractDomainService <RESPTYPE> implements DomainService<RESPTYPE> {
	
	protected final DomainRepository repository;
	protected final ValidationService validationService;
	
	private final Class <RESPTYPE> type;
	
	protected AbstractDomainService(DomainRepository repository, ValidationService service, Class <RESPTYPE> type) {
		this.repository = repository;
		this.type = type;
		this.validationService = service;
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public RESPTYPE persist(RESPTYPE item) throws ValidationException {
		validate(item);
		return repository.create(item);
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public RESPTYPE update(RESPTYPE item) throws ValidationException {
		validate(item);
		return repository.update(item);
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public void delete(RESPTYPE item) {
		repository.delete(item);
	}
	
	@Override
	@Transactional (readOnly=true)
	public Collection<RESPTYPE> readAll(PropertyFilterWrapper... filters) {
		return repository.readAll(type, filters);
	}
	
	protected abstract Object getIdFromItem (RESPTYPE item);
	
	private void validate (RESPTYPE item) throws ValidationException {
		List <Violation> violations = validationService.validate(item);
		if (!violations.isEmpty()) {
			throw new ValidationException(violations);
		}
	}
}
