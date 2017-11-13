package de.nak.iaa.housework.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.service.validation.ValidationException;
import de.nak.iaa.housework.service.validation.ValidationService;
import de.nak.iaa.housework.service.validation.Violation;

/**
 * Die abstrakte Implementierung eines {@link DomainService}.
 * Delegiert die Standard-Methoden der Schnittstelle (CRUD) an das {@link DomainRepository}
 * 
 * @author da0015 14096
 *
 * @param <RESPTYPE> der Typ dieses Service
 */
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
	@Transactional
	public RESPTYPE persist(RESPTYPE item, boolean validate) throws ValidationException {
		if (alreadyExists(item)) {
			throw new ValidationException(new Violation("Die Entität [" + item + "] existiert bereits!"));
		}
		if (validate) {
			validate(item);
		}
		return repository.create(item);
	}
	@Override
	@Transactional
	public RESPTYPE persist(RESPTYPE item) throws ValidationException {
		return persist (item, true);
	}
	@Override
	@Transactional
	public RESPTYPE update(RESPTYPE item, boolean validate) throws ValidationException {
		if (validate) {
			validate(item);
		}
		return repository.update(item);
	}
	@Override
	@Transactional
	public RESPTYPE update(RESPTYPE item) throws ValidationException {
		return update (item, true);
	}
	@Override
	@Transactional
	public void delete(RESPTYPE item) {
		repository.delete(item);
	}
	@Override
	@Transactional (readOnly=true)
	public List<RESPTYPE> readAll() {
		return repository.readAll(type);
	}	
	
	/**
	 * Validiert ein item mittels des {@link ValidationService}. Kommt es zu {@link Violation} so wird eine 
	 * {@link ValidationException} ausgelöst.
	 * @param item
	 * @throws ValidationException
	 */
	protected void validate (RESPTYPE item) throws ValidationException {
		Set <Violation> violations = validationService.validate(item);
		if (!violations.isEmpty()) {
			throw new ValidationException(violations);
		}
	}
	/**
	 * Prüft ob ein Element bereits existiert.
	 * 
	 * @param item the item to check
	 */
	protected boolean alreadyExists (RESPTYPE item) throws ValidationException {
		Object databaseId = extractDatabaseID(item);
		return databaseId != null && repository.find(this.type, databaseId) != null;
	}
	
	protected abstract Object extractDatabaseID (RESPTYPE entity);
}
