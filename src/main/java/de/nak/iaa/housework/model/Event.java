package de.nak.iaa.housework.model;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Diese Klasse repräsentiert alle Sorten von Veranstaltungen. Der Typ der Veranstaltung
 * also ob Klausur/Vorlesung/Seminar wird hierbei über {@link Event#type} festgehalten werden.
 * Der unterschiedliche Typ wird benötigt um gegebenenfalls eine unterschiedliche fachliche Behandlung 
 * vorzunehmen. Veranstaltungen werden über eine ID eindeutig identifiziert.
 * 
 * @author Nico Kriebel
 */
@Entity
@Table(name = "EVENT")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic (optional = false)
	private final EventType type;
	@Basic (optional = false)
	private String title;
	
	@Basic
	private LocalDate start;
	@Basic
	private LocalDate end;
	@Basic
	private int changeDuration;
	@ManyToOne
	private Room room;
	@ManyToOne
	private Lecturer lecturer;
		
	public Event (EventType type, String title) {
		this.type = type;
		this.title = title;
	}
	public Event (EventType type, String title, LocalDate start, LocalDate end) {
		this (type, title);
		this.start = start;
		this.end = end;
	}
	public Event(EventType type, String title, LocalDate start, LocalDate end, 
			Room room, Lecturer lecturer, int changeDurationInMinutes) {
		this (type, title, start, end);
		this.room = room;
		this.lecturer = lecturer;
		this.changeDuration = changeDurationInMinutes;
	}
	
	public long getId() {
		return id;
	}
	public EventType getType() {
		return type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
