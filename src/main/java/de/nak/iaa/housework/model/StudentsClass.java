package de.nak.iaa.housework.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Repräsentation einer Zenturie, welche über ihren Namen eindeutig identifiziert werden kann. Dieser setzt sich
 * zusammen aus dem {@link FieldOfStudy} also der Studienrichtung, dem Jahrgang sowie der Klasse(a,b,c,d, usw.)
 * 
 * @author Nico Kriebel
 *
 */
@Entity
@Table (name = "STUDENT_CLASS")
@JsonIgnoreProperties(ignoreUnknown=true)
public class StudentsClass {

	public static final String PROPERTY_ID_FIELD_OF_STUDY = "id.fieldOfStudy";
	public static final String PROPERTY_ID_YEAR = "id.year";
	public static final String PROPERTY_ID_FORM = "id.form";
	public static final String PROPERTY_NAME_EVENTS_TO_ATTEND = "eventsToAttend";
	
	@EmbeddedId
	private StudentsClassId id;
	@Basic
	private int size;
	@Basic
	private int minimalBreakTime;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JsonIgnore /* ignore the events to prevent that the events are fetched when not needed */ 
	private Set <Event> eventsToAttend = new HashSet<>();
	
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
	public void addEvents (List <Event> events) {
		this.eventsToAttend.addAll(events);
	}
	public void cancelEvent (Event event) {
		this.eventsToAttend.remove(event);
	}
	public Set<Event> getEventsToAttend() {
		return Collections.unmodifiableSet(eventsToAttend);
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
