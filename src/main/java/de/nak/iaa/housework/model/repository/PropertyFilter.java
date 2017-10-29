package de.nak.iaa.housework.model.repository;

import de.nak.iaa.housework.model.repository.PropertyFilterWrapper.Connector;

/**
 * Schnittstelle für einen Filter, welcher genutzt werde kann um über das {@link DomainRepository} persistente
 * Entitäten zu lesen, die bestimmten Kriterien entsprechen.
 * 
 * @author Nico Kriebel
 */
public class PropertyFilter { 

	/**
	 * Definiert den Operator welcher zum Filtern über das {@link DomainRepository} mittels {@link PropertyFilter}
	 * genutzt wird.
	 * 
	 * @author Nico Kriebel
	 */
	public static enum Operator {

		EQ,
		NOTEQ,
		LESS,
		LESSEQ,
		GREATER,
		GREATEREQ;
	}
	
	private final Operator operator;
	private final String propertyName;
	private final Object propertyValue;
	
	public PropertyFilter(Operator operator, String propertyName, Object propertyValue) {
		super();
		this.operator = operator;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	
	public Operator getOperator() {
		return operator;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public Object getPropertyValue() {
		return propertyValue;
	}	
	
	public PropertyFilterWrapper wrap (Connector connector) {
		return new PropertyFilterWrapper(connector, this);
	}
}
