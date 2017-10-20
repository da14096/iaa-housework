package de.nak.iaa.housework.model;

public enum FieldOfStudy {

	A ('A', "Angewandte Informatik"),
	B ('B', "Betriebswirtschaftslehre"),
	I ('I', "Wirtschaftsinformatik"),
	W ('W', "Wirtschaftsingenieurswesen");
	
	
	private char identifier;
	private String name;
	
	private FieldOfStudy (char identifier, String name) {
		this.identifier = identifier;
		this.name = name;
	}
	
	public char getIdentifier() {
		return identifier;
	}
	public String getName() {
		return name;
	}
}
