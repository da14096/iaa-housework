package de.nak.iaa.housework.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Diese Klasse repr�sentiert alle Sorten von Veranstaltungen. Der Typ der Veranstaltung
 * also ob Klausur/Vorlesung/Seminar wird hierbei �ber {@link Event#type} festgehalten werden.
 * Der unterschiedliche Typ wird ben�tigt um gegebenenfalls eine unterschiedliche fachliche Behandlung 
 * vorzunehmen. Veranstaltungen werden �ber eine ID eindeutig identifiziert.
 * 
 * @author Nico Kriebel
 */
@Entity
@Table(name = "EVENT")
public class Event {

	public static final String PROPERTY_NAME_EVENT_TYPE = "type";
	public static final String PROPERTY_NAME_TITLE = "title";
	
	public static final String PROPERTY_NAME_START = "start";
	public static final String PROPERTY_NAME_END = "end";
	public static final String PROPERTY_NAME_CHANGE_DURATION = "changeDuration";
	public static final String PROPERTY_NAME_ROOMS = "rooms";
	public static final String PROPERTY_NAME_LECTURER = "lecturer";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Basic (optional = false)
	private EventType type;
	@Basic (optional = false)
	private String title;
	
	@Basic
	private LocalDateTime start;
	@Basic
	private LocalDateTime end;
	@Basic
	private int changeDuration;
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Room> rooms = new HashSet<>();
	@ManyToOne
	private Lecturer lecturer;
		
	public Event() { }
	
	public Event (EventType type, String title) {
		this.type = type;
		this.title = title;
	}
	public Event (EventType type, String title, LocalDateTime start, LocalDateTime end) {
		this (type, title);
		this.start = start;
		this.end = end;
	}
	public Event(EventType type, String title, LocalDateTime start, LocalDateTime end, 
			Set<Room> rooms, Lecturer lecturer, int changeDurationInMinutes) {
		this (type, title, start, end);
		this.lecturer = lecturer;
		this.changeDuration = changeDurationInMinutes;
		this.rooms.addAll(rooms);
	}
	
	public Long getId() {
		return id;
	}
	public void setType(EventType type) {
		this.type = type;
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
	public void addRoom(Room room) {
		rooms.add(room);
	}
	public void removeRoom (Room room) {
		rooms.remove(room);
	}
	public Set<Room> getRooms() {
		return Collections.unmodifiableSet(rooms);
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
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
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return title;
	}
}
