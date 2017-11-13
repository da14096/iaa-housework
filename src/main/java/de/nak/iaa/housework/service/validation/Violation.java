package de.nak.iaa.housework.service.validation;

import java.io.Serializable;

/**
 * Diese Klasse entspricht einer fehlgeschlagenen Validierung, welche eine Nachricht als Parameter trägt.
 * 
 * @author da0015 14096
 */
public class Violation implements Serializable {

	private static final long serialVersionUID = -4490906054457238211L;

	private final String message;
	
	public Violation(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Violation other = (Violation) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
	
}
