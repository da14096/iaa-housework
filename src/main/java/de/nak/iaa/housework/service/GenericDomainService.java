package de.nak.iaa.housework.service;

import java.util.Collection;

import de.nak.iaa.housework.model.repository.DomainRepository;

abstract class GenericDomainService <RESPTYPE> implements DomainService<RESPTYPE> {

	private final DomainRepository repository;
	private final Class <RESPTYPE> type;
	
	protected GenericDomainService(DomainRepository repository, Class <RESPTYPE> type) {
		this.repository = repository;
		this.type = type;
	}
	
	@Override
	public RESPTYPE update(RESPTYPE item) {
		return repository.update(item);
	}
	@Override
	public void delete(RESPTYPE item) {
		repository.delete(item);
	}
	@Override
	public Collection<RESPTYPE> readAll() {
		return repository.readAll(type);
	}
}
