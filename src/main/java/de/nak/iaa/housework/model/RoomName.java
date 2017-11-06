package de.nak.iaa.housework.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Diese Klasse wird genutzt um den komplexen Schlüssel für Räume abzubilden
 * 
 * @author Nico Kriebel
 */
@Embeddable
public class RoomName implements Serializable {

	private static final long serialVersionUID = -3246470398118320108L;

	private Building building;
	private int number;
	
	public RoomName() {/* necessary for hibernate*/}
	
	public RoomName (Building building, int number) {
		this.building = building;
		this.number = number;
	}
	
	public Building getBuilding() {
		return building;
	}
	public int getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((building == null) ? 0 : building.hashCode());
		result = prime * result + number;
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
		RoomName other = (RoomName) obj;
		if (building != other.building)
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return building.toString() + number;
	}
	
}
