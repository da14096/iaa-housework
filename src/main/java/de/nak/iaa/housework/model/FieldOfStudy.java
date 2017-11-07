package de.nak.iaa.housework.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FieldOfStudy {

	A ('A', "Angewandte Informatik"),
	B ('B', "Betriebswirtschaftslehre"),
	I ('I', "Wirtschaftsinformatik"),
	W ('W', "Wirtschaftsingenieurswesen");
	
	private char abreviation;
	private String description;
	
	private FieldOfStudy (char abreviation, String description) {
		this.abreviation = abreviation;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public char getAbreviation() {
		return abreviation;
	}	
	@JsonCreator
	public static FieldOfStudy parse (@JsonProperty(value="identifier") char identifier) {
		for (FieldOfStudy type: values()) {
			if (type.getAbreviation() == identifier) {
				return type;
			}
		}
		return A;
	}
}
