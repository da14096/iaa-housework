package de.nak.iaa.housework.model.repository;

public class NotConsitentException extends RuntimeException {

	private static final long serialVersionUID = -5036554597455766975L;

	
	public NotConsitentException() { }
	
	public NotConsitentException(String message) {
		super(message);
	}
}
