package de.nak.iaa.housework.model.repository;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Standard-Implementierung des Repositorys. Da die Methoden des Persistenzkontexts sehr generisch sind, 
 * verwenden wir eine allgemeine Implementierung um die Wart- und Erweiterbarkeit möglichst groß zu halten. 
 * 
 * @author Nico Kriebel
 */
@Repository
public class DefaultDomainRepository implements DomainRepository {
	
	/** Name der Variablen, welche bei der jpQuery-Language verwendet wird*/
	private static final String DATA_VAR_NAME = " e";
	/** Prefix eines Select-Statements in der jpQuery-Language*/
	private static final String SELECT_CLAUSE_PREFIX = "SELECT" + DATA_VAR_NAME + " FROM ";
		
	@PersistenceContext
	private EntityManager entityManager;
		
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public <TYPE> void delete(final TYPE item) {
		entityManager.remove(item);
	}
	@Override
	public <TYPE> TYPE update(final TYPE item) {
		if (entityManager.contains(item)) {
			entityManager.merge(item);
		} else {
			try {
				entityManager.persist(item);
			} catch (EntityExistsException e) {
				throw new NotConsitentException("Die Entität [" + item + "] ist bereits persistent bzw. es existiert"
					+ "eine andere persistente Entität mit dieser ID. Wenn Sie ein update dieser Entität vornehmen"
					+ " möchten stellen Sie bitte sicher, dass sie diese Entität über eine der read-Methoden abrufen!");
			}
		}
		return item;
	}
	
	@Override
	public <TYPE> TYPE find(Class<TYPE> targetType, Object id) {
		return entityManager.find(targetType, id);
	}
	@Override
	public <TYPE> List<TYPE> readAll(Class <TYPE> targetType) {
		return entityManager.createQuery(buildReadAllQueryForType(targetType), targetType).getResultList();
	}
	
	/** Erzeugt eine Query zum Lesen aller Elemente eines bestimmten Typs */
	private String buildReadAllQueryForType (Class <?> type) {
		StringBuilder builder = new StringBuilder();
		builder.append(SELECT_CLAUSE_PREFIX);
		builder.append(type.getSimpleName());
		builder.append(DATA_VAR_NAME);
		return builder.toString();
	}
}
