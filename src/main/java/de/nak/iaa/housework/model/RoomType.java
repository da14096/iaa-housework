package de.nak.iaa.housework.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Die unterschiedlichen Raumtypen
 * @author da0015
 *
 */
public enum RoomType {

	LECTURE("Zenturienraum"),
	COMPUTER("Computerraum"),
	OTHER("Andere");
	
	@JsonValue
	private String description;
	
	private RoomType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	@JsonCreator
	public static RoomType fromDescription (String description) {
		for (RoomType type: values()) {
			if (type.getDescription().equals(description)) {
				return type;
			}
		}
		return null;
	}
}
