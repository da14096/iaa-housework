package de.nak.iaa.housework.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Repräsentation einer Zenturie, welche über ihren Namen eindeutig identifiziert werden kann.
 * 
 * @author Nico Kriebel
 *
 */
@Entity
@Table (name = "STUDENT_CLASS")
public class StudentsClass extends EventParticipant {

	@Id
	private final String name;
	@Basic
	private int size;
	
	
	public StudentsClass (String name) {
		this.name = name;
	}
	public StudentsClass(String name, int size, int minimalBreakTime) {
		this (name);
		super.setMinimalBreakTime(minimalBreakTime);
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
		StudentsClass other = (StudentsClass) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
