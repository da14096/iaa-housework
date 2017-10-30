package de.nak.iaa.housework.service;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.repository.DomainRepository;

abstract class AbstractDomainService <RESPTYPE> implements DomainService<RESPTYPE> {
	
	private final DomainRepository repository;
	private final Class <RESPTYPE> type;
	
	protected AbstractDomainService(DomainRepository repository, Class <RESPTYPE> type) {
		this.repository = repository;
		this.type = type;
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public RESPTYPE persist(RESPTYPE item) {
		return repository.create(item);
	}
	
	@Override
	@Transactional (rollbackFor=Exception.class)
	public RESPTYPE update(RESPTYPE item) {
		return repository.update(item);
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
	
	protected abstract Object getIdFromItem (RESPTYPE item);
}
