package de.nak.iaa.housework.model;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Repräsentation einer Zenturie, welche über ihren Namen eindeutig identifiziert werden kann. Dieser setzt sich
 * zusammen aus dem {@link FieldOfStudy} also der Studienrichtung, dem Jahrgang sowie der Klasse(a,b,c,d, usw.)
 * 
 * @author Nico Kriebel
 *
 */
@Entity
@Table (name = "STUDENT_CLASS")
public class StudentsClass extends EventParticipant {

	@EmbeddedId
	private StudentsClassId id;
	@Basic
	private int size;
	
	public StudentsClass(StudentsClassId name) {
		id = name;
	}
	public StudentsClass(StudentsClassId name, int size, int minimalBreakTime) {
		this (name);
		super.setMinimalBreakTime(minimalBreakTime);
		this.size = size;
	}
	public String getName() {
		return id.getFormName();
	}
	public FieldOfStudy getFieldOfStudy() {
		return id.getFieldOfStudy();
	}
	public int getYr() {
		return id.getYear();
	}
	public char getForm() {
		return id.getForm();
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
