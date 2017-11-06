package de.nak.iaa.housework.model;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.nak.iaa.housework.model.repository.DomainRepository;

/**
 * Diese Klasse repräsentiert einen Raum. Dieser kann eindeutig über seinen Namen identifiziert werden.
 * 
 * @author Nico Kriebel
 */
@Entity
@Table (name = "ROOM")
public class Room {
	
	public static final String PROPERTY_BUILDING = "name" + DomainRepository.PROPERTY_NAVIGATOR + "building";
	
	@EmbeddedId
	private RoomName name;
	@Basic
	private Integer capacity;
	@Basic
	private int changeDuration;
	
	public Room() {
		
	}
	public Room(RoomName name) {
		this.name = name;
	}
	public Room(RoomName name, int capacity) {
		this (name);
		this.capacity = capacity;
	}
	
	public RoomName getName() {
		return name;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getChangeDuration() {
		return changeDuration;
	}
	public void setChangeDuration(int changeDuration) {
		this.changeDuration = changeDuration;
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
	@Override
	public String toString() {
		return name.toString();
	}
}
