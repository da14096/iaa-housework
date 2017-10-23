package de.nak.iaa.housework.service;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.repository.NotConsitentException;
import de.nak.iaa.housework.model.repository.DomainRepository;

abstract class GenericDomainService <RESPTYPE> implements DomainService<RESPTYPE> {

	private final DomainRepository repository;
	private final Class <RESPTYPE> type;
	
	protected GenericDomainService(DomainRepository repository, Class <RESPTYPE> type) {
		this.repository = repository;
		this.type = type;
	}
	
	@Override
	@Transactional (rollbackFor=NotConsitentException.class)
	public RESPTYPE save(RESPTYPE item) throws AlreadyExistsException {
		try {
			return repository.update(item);
		} catch (NotConsitentException e) {
			throw new AlreadyExistsException("[" +item + "] existiert bereits!");
		}
	}
	
	@Override
	public RESPTYPE update(RESPTYPE item) {
		throw new UnsupportedOperationException("The [" + this + "] does not support an update operation!");
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public void delete(RESPTYPE item) {
		repository.delete(item);
	}
	
	@Override
	@Transactional (readOnly=true)
	public Collection<RESPTYPE> readAll() {
		return repository.readAll(type);
	}
}
