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
public class Lecturer {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long personnelNumber;
	@Basic (optional = false)
	private String name;
	@Basic (optional = false)
	private String surname;
	@Basic (optional = false)
	private int minimalBreakTime;
	
	public Lecturer () { }
	public Lecturer(String name, String surname, int minimalBreakTime) {
		this.name = name;
		this.surname = surname;
		this.minimalBreakTime = minimalBreakTime;
	}
	
	public Long getPersonnelNumber() {
		return personnelNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSurname() {
		return surname;
	}
	public void setMinimalBreakTime(int minimalBreakTime) {
		this.minimalBreakTime = minimalBreakTime;
	}
	public int getMinimalBreakTime() {
		return minimalBreakTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personnelNumber == null) ? 0 : personnelNumber.hashCode());
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
		if (personnelNumber == null) {
			if (other.personnelNumber != null)
				return false;
		} else if (!personnelNumber.equals(other.personnelNumber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name + " " + surname + "(" + personnelNumber + ")";
	}
	
	
}
