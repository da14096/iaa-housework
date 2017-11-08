package de.nak.iaa.housework.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Repr�sentation einer Zenturie, welche �ber ihren Namen eindeutig identifiziert werden kann. Dieser setzt sich
 * zusammen aus dem {@link FieldOfStudy} also der Studienrichtung, dem Jahrgang sowie der Klasse(a,b,c,d, usw.)
 * 
 * @author Nico Kriebel
 *
 */
@Entity
@Table (name = "STUDENT_CLASS")
public class StudentsClass {

	public static final String PROPERTY_ID_FIELD_OF_STUDY = "id.fieldOfStudy";
	public static final String PROPERTY_ID_YEAR = "id.year";
	public static final String PROPERTY_ID_FORM = "id.form";
	
	@EmbeddedId
	private StudentsClassId id;
	@Basic
	private int size;
	@Basic
	private int minimalBreakTime;
	
	@ManyToMany
	@JsonIgnore /* ignore the events to prevent that the events are fetched when not needed */ 
	private List <Event> eventsToAttend = new ArrayList<>();
	
	public StudentsClass() { }
	
	public StudentsClass(StudentsClassId name) {
		id = name;
	}
	public StudentsClass(StudentsClassId name, int size, int minimalBreakTime) {
		this (name);
		this.size = size;
		this.minimalBreakTime = minimalBreakTime;
	}
	public String getName() {
		return id.getFormName();
	}
	public FieldOfStudy getFieldOfStudy() {
		return id.getFieldOfStudy();
	}
	public int getYear() {
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
	public StudentsClassId getId() {
		return id;
	}
	public void setMinimalBreakTime(int minimalBreakTime) {
		this.minimalBreakTime = minimalBreakTime;
	}
	public int getMinimalBreakTime() {
		return minimalBreakTime;
	}
	
	public void addEvent (Event event) {
		this.eventsToAttend.add(event);
	}
	public void cancelEvent (Event event) {
		this.eventsToAttend.remove(event);
	}
	public List<Event> getEventsToAttend() {
		return Collections.unmodifiableList(eventsToAttend);
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
	@Override
	public String toString() {
		return id.getFormName();
	}
}
