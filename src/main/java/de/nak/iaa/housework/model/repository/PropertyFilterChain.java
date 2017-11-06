package de.nak.iaa.housework.model.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyFilterChain {

	/**
	 * Definiert einen Konnektor, mit dem {@link PropertyFilter} miteinander verknüpft werden können.
	 * 
	 * @author Nico Kriebel
	 */
	public static enum Connector {
		
		AND,
		OR;
	}
	
	private final List <PropertyFilterConnector> propertyFilters = new ArrayList<>();
	
	private PropertyFilterChain () {	}
	
	public PropertyFilterChain appendFilter (PropertyFilter filter, Connector connector) {
		propertyFilters.add(new PropertyFilterConnector(connector, filter));
		return this;
	}
	public boolean hasFilters () {
		return !propertyFilters.isEmpty();
	}
	public List <PropertyFilterConnector> getFilters () {
		return Collections.unmodifiableList(propertyFilters);
	}
	
	public static PropertyFilterChain emptyChain () {
		return new PropertyFilterChain();
	}
	public static PropertyFilterChain startWith (PropertyFilter filter) {
		PropertyFilterChain chain = new PropertyFilterChain();
//		take this as the simple connection
		chain.appendFilter(filter, Connector.AND);
		return chain;
	}
	
	/**
	 * Reichert einen {@link PropertyFilter} um einen {@link Connector} an, der festlegt, wie der Filter mit einem
	 * vorangehenden Filter verknüpft werden soll.
	 * 
	 * @author Nico Kriebel
	 */
	public static class PropertyFilterConnector {
		
		private final Connector connector;
		private final PropertyFilter filter;
		
		private PropertyFilterConnector(Connector connector, PropertyFilter filter) {
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

}
