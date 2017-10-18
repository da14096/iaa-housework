package de.nak.iaa.housework.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Diese Klasse repräsentiert einen Raum. Dieser kann eindeutig über seinen Namen identifiziert werden.
 * 
 * @author Nico Kriebel
 */
@Entity
@Table (name = "ROOM")
public class Room {
	
	@Id
	private final String name;
	@Basic
	private int capacity;
	@Basic
	private int changeDuration;
	
	public Room(String name) {
		this.name = name;
	}
	public Room(String name, int capacity) {
		this (name);
		this.capacity = capacity;
	}
	
	public String getName() {
		return name;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Room other = (Room) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
