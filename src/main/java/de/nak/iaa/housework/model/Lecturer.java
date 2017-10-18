package de.nak.iaa.housework.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Diese Klasse repräsentiert einen Dozenten, der über seine Personalnummer identifiziert wird. 
 * 
 * @author Nico Kriebel
 */
@Entity
@Table (name = "LECTURER")
public class Lecturer extends EventParticipant {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long personnelNumber;
	@Basic (optional = false)
	private String name;
	
	public Lecturer(String name, int minimalBreakTime) {
		super (minimalBreakTime);
		this.name = name;
	}
	
	public long getPersonnelNumber() {
		return personnelNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (personnelNumber ^ (personnelNumber >>> 32));
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
		Lecturer other = (Lecturer) obj;
		if (personnelNumber != other.personnelNumber)
			return false;
		return true;
	}
}
