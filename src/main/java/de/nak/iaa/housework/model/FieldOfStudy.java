package de.nak.iaa.housework.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FieldOfStudy {

	A ('A', "Angewandte Informatik"),
	B ('B', "Betriebswirtschaftslehre"),
	I ('I', "Wirtschaftsinformatik"),
	W ('W', "Wirtschaftsingenieurswesen");
	
	private char identifier;
	private String description;
	
	private FieldOfStudy (char identifier, String description) {
		this.identifier = identifier;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public char getIdentifier() {
		return identifier;
	}
}
