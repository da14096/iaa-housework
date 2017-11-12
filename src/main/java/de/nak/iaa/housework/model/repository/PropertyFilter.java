package de.nak.iaa.housework.model.repository;

/**
 * Schnittstelle f�r einen Filter, welcher genutzt werde kann um �ber das {@link DomainRepository} persistente
 * Entit�ten zu lesen, die bestimmten Kriterien entsprechen.
 * 
 * @author Nico Kriebel
 */
public class PropertyFilter { 

	/** Dieser String identifiziert die Entit�t und kann genutzt werden um beispielsweise bei der Suche zweier R�ume
	 * einen bestimmtem Raum auszuschlie�en */
	private static final String PROPERTY_NAME_ENTITY = "";
	
	/**
	 * Definiert den Operator welcher zum Filtern �ber das {@link DomainRepository} mittels {@link PropertyFilter}
	 * genutzt wird.
	 * 
	 * @author Nico Kriebel
	 */
	public static enum Operator {

		EQ,
		NOTEQ,
		MEMBER,
		GREATER,
		GREATEREQ,
		LESS,
		LESSEQ;
	}
	
	private final Operator operator;
	private final String propertyName;
	private final Object propertyValue;
	
	public PropertyFilter(Object propertyValue, Operator operator, String propertyName) {
		super();
		this.operator = operator;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	public PropertyFilter(Object propertyValue, Operator operator) {
		this (propertyValue, operator, PROPERTY_NAME_ENTITY);
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
}
