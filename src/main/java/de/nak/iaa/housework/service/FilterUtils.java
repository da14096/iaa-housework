package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;


public class FilterUtils {

	private final DomainRepository repository;
	
	private FilterUtils (DomainRepository repository) {
		this.repository = repository;
	}
	
	public Set <Event> getAllOverlappingEvents (LocalDateTime start, LocalDateTime end, PropertyFilter... additionalFilters) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("Start and End must be present to get overlapping events!");
		}
		
		Set <Event> overlappingEvents = new HashSet<>();
//			get the events that start in the interval
		PropertyFilter startGrStartFilter = new PropertyFilter(start, Operator.LESSEQ, Event.PROPERTY_NAME_START);
		PropertyFilter startLessEndFilter = new PropertyFilter(end, Operator.GREATEREQ, Event.PROPERTY_NAME_START);	
		PropertyFilterChain chain = PropertyFilterChain
										.startWith(startGrStartFilter)
										.appendFilter(startLessEndFilter, Connector.AND);
		overlappingEvents.addAll(repository.readAll(Event.class, appendFilters(chain, additionalFilters)));
		
//			get the events that end in the interval
		PropertyFilter endGrStartFilter = new PropertyFilter(start, Operator.LESSEQ, Event.PROPERTY_NAME_END);
		PropertyFilter endLessEndFilter = new PropertyFilter(end, Operator.GREATEREQ, Event.PROPERTY_NAME_END);
		chain = PropertyFilterChain.startWith(endGrStartFilter).appendFilter(endLessEndFilter, Connector.AND);
		overlappingEvents.addAll(repository.readAll(Event.class, appendFilters(chain, additionalFilters)));
		
//			get the events that start before and end after the interval
		PropertyFilter startBefStartFilter = new PropertyFilter(start, Operator.GREATEREQ, Event.PROPERTY_NAME_START);
		PropertyFilter endAftEndFilter = new PropertyFilter(end, Operator.LESSEQ, Event.PROPERTY_NAME_END);
		chain = PropertyFilterChain.startWith(startBefStartFilter).appendFilter(endAftEndFilter, Connector.AND);
		overlappingEvents.addAll(repository.readAll(Event.class, appendFilters(chain, additionalFilters)));
		
		return overlappingEvents;
	}
	
	private PropertyFilterChain appendFilters(PropertyFilterChain chain, PropertyFilter... filters) {
		for (PropertyFilter filter: filters) {
			chain.appendFilter(filter, Connector.AND);
		}
		return chain;
	}
	
	public static FilterUtils instance (DomainRepository repository) {
		return new FilterUtils(repository);
	}
}
