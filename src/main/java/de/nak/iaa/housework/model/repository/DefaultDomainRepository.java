package de.nak.iaa.housework.model.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.PropertyFilterConnector;

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
	/** Prefix von Variablen, die als Eingabe für eine Query dienen */
	private static final String INPUT_VAR_NAME_PREFIX = "iv";
	/** Notation um einen Input-Parameter zu definieren*/
	private static final String INPUT_PARAMETER_IDENTIFIER= " :";
	
	/** Prefix eines Select-Statements in der jpQuery-Language*/
	private static final String SELECT_CLAUSE_PREFIX = "SELECT" + DATA_VAR_NAME + " FROM ";
	
	/** Prefix einer Where-Clause in der jpQuery-Language*/
	private static final String WHERE_CLAUSE_PREFIX = " WHERE";
	/** Prefix einer Between-Clause in der jpQuery-Language*/	
	/** Connects where or between clause*/
	private static final String AND_CONNECTOR = " AND";
	/** Connects where or between clause*/
	private static final String OR_CONNECTOR = " OR";
	
	private static final String EQ_OPERATOR = " =";
	private static final String NOTEQ_OPERATOR = " !=";
	private static final String LESS_EQ_OPERATOR = " <=";
	private static final String GREATER_EQ_OPERATOR = " >=";
	private static final String LESS_OPERATOR = " <";
	private static final String GREATER_OPERATOR = " >";
	private static final String IN_OPERATOR = " IN";
	private static final String NOT_IN_OPERATOR = " NOT IN";
	
	@PersistenceContext
	private EntityManager entityManager;
		
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public <TYPE> void delete(final TYPE item) {
//		first merge the item to get the correct persistent entity, then delete it
		final TYPE persistentInstance = entityManager.merge(item);
		entityManager.remove(persistentInstance);
	}
	@Override
	public <TYPE> TYPE update(final TYPE item) {
		return entityManager.merge(item);
	}
	@Override
	public <TYPE> TYPE create(TYPE item) {
		entityManager.persist(item);
		return item;
	}
	
	@Override
	public <TYPE> TYPE find(Class<TYPE> targetType, Object id) {
		return entityManager.find(targetType, id);
	}
	@Override
	public <TYPE> List<TYPE> readAll(Class<TYPE> targetType) {
		return readAll(targetType, PropertyFilterChain.emptyChain());
	}
	@Override
	public <TYPE> List<TYPE> readAll(Class <TYPE> targetType, PropertyFilterChain propertyFilters) {
		StringBuilder builder = new StringBuilder(buildReadAllClauseForType(targetType));
		Map <String, Object> queryParameter = new HashMap <>();
		if (propertyFilters.hasFilters()) {
			builder.append(WHERE_CLAUSE_PREFIX);
			
			List <PropertyFilterConnector> filters = propertyFilters.getFilters();		
			for (int i = 0; i < filters.size(); i++) {
				PropertyFilterConnector connector = filters.get(i);
				PropertyFilter filter = connector.getFilter();
				if (i > 0) {
					builder.append(parseConnector(connector.getConnector()));
				}
				String inputVarName = INPUT_VAR_NAME_PREFIX + i;
				builder.append(parsePropertyFilter(filter, inputVarName));
				queryParameter.put(inputVarName, filter.getPropertyValue());
			}
		}	
		
		TypedQuery <TYPE> selectQuery = entityManager.createQuery(builder.toString(), targetType);
		queryParameter.forEach((k, v) -> selectQuery.setParameter(k, v));
		return selectQuery.getResultList();
	}
	
	/** Erzeugt eine Query zum Lesen aller Elemente eines bestimmten Typs */
	private String buildReadAllClauseForType (Class <?> type) {
		StringBuilder builder = new StringBuilder();
		builder.append(SELECT_CLAUSE_PREFIX);
		builder.append(type.getSimpleName());
		builder.append(DATA_VAR_NAME);
		return builder.toString();
	}
	
	private String parsePropertyFilter (PropertyFilter filter, String inputParameterName) {
		StringBuilder builder = new StringBuilder(INPUT_PARAMETER_IDENTIFIER);
		builder.append(inputParameterName);
		builder.append(parseOperator(filter.getOperator()));
		builder.append(DATA_VAR_NAME);
		String propertyName = filter.getPropertyName();
		if (propertyName != null && !propertyName.isEmpty()) {
			builder.append(PROPERTY_NAVIGATOR);
			builder.append(propertyName);
		}		
		return builder.toString();
	}
	
	private String parseOperator (Operator type) {
		switch (type) {
		case EQ:
			return EQ_OPERATOR;
		case NOTEQ:
			return NOTEQ_OPERATOR;
		case IN:
			return IN_OPERATOR;
		case NOT_IN:
			return NOT_IN_OPERATOR;
		case LESS:
			return LESS_OPERATOR;
		case LESSEQ:
			return LESS_EQ_OPERATOR;
		case GREATER:
			return GREATER_OPERATOR;
		case GREATEREQ:
			return GREATER_EQ_OPERATOR;
		default: 
			throw new IllegalArgumentException("No Operator defined for FilterType [" + type + "]");
		}
	}
	
	private String parseConnector (Connector connector) {
		switch(connector) {
		case AND:
			return AND_CONNECTOR;
		case OR:
			return OR_CONNECTOR;
		default:
			throw new IllegalArgumentException("No Connector-String defined for Connector [" + connector + "]");
		}
	}
}
