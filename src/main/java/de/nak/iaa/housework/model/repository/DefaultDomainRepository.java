package de.nak.iaa.housework.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;

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
	
	/** Speichert zu einer Klasse die zugehörige Tabelle. Der hier hinterlegte Name muss mit dem in der
	 * {@link Table}-Annotation übereinstimmen. */
	private static final Map <Class <?>, String> TABLE_NAME = initTypeToTableMapping();
	
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
			entityManager.persist(item);
		}
		return item;
	}
	@Override
	public <TYPE> Collection<TYPE> readAll(Class <TYPE> targetType) {
		return entityManager.createQuery(buildReadAllQueryForType(targetType), targetType).getResultList();
	}
	
	/** Erzeugt eine Query zum Lesen aller Elemente eines bestimmten Typs */
	private String buildReadAllQueryForType (Class <?> type) {
		if (!TABLE_NAME.containsKey(type)) {
			throw new IllegalStateException("Unknown datatable for type [" + type +"]");
		}
		StringBuilder builder = new StringBuilder();
		builder.append(SELECT_CLAUSE_PREFIX);
		builder.append(TABLE_NAME.get(type));
		builder.append(DATA_VAR_NAME);
		return builder.toString();
	}
	
	/** Liefert die Tabellennamen für die Klassen des Persistenzkontexts */
	private static final Map <Class<?>, String> initTypeToTableMapping () {
		Map <Class<?>, String> result = new HashMap<>();
		result.put(Event.class, "EVENT");
		result.put(Lecturer.class, "LECTURER");
		result.put(Room.class, "ROOM");
		result.put(StudentsClass.class, "STUDENT_CLASS");
		return result;
	}
}
