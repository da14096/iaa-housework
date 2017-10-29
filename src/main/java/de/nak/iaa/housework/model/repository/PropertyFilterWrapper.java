package de.nak.iaa.housework.model.repository;

/**
 * Reichert einen {@link PropertyFilter} um einen {@link Connector} an, der festlegt, wie der Filter mit einem
 * vorangehenden Filter verknüpft werde soll.
 * 
 * @author Nico Kriebel
 */
public class PropertyFilterWrapper {

	/**
	 * Definiert einen Konnektor, mit dem {@link PropertyFilter} miteinander verknüpft werden können.
	 * 
	 * @author Nico Kriebel
	 */
	public static enum Connector {
		
		AND,
		OR;
	}
	
	private final Connector connector;
	private final PropertyFilter filter;
	
	public PropertyFilterWrapper(Connector connector, PropertyFilter filter) {
		super();
		this.connector = connector;
		this.filter = filter;
	}

	public PropertyFilter getFilter() {
		return filter;
	}
	public Connector getConnector() {
		return connector;
	}
}
