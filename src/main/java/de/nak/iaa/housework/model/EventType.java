package de.nak.iaa.housework.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Die unterschiedlichen Veranstaltungstypen.
 * 
 * @author Nico Kriebel
 */
public enum EventType {

	LECTURE("Vorlesung"),
	EXAMN("Klausur"),
	SEMINAR("Seminar"),
	COMPULSORY_COURSE("Wahlpflichtkurs");
	
	@JsonValue
	private String description;
	
	private EventType (String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	@JsonCreator
	public static EventType fromDescription (String description) {
		for (EventType type: values()) {
			if (type.getDescription().equals(description)) {
				return type;
			}
		}
		return null;
	}
}
