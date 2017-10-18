package de.nak.iaa.housework.model;

import javax.persistence.Basic;

/**
 * Abstrakte Oberklasse für Objekte, welche an einer Veranstaltung teilnehmen (vorgesehen für Dozenten/Zenturien)
 * 
 * @author Nico Kriebel
 */
public abstract class EventParticipant {

	@Basic
	private int minimalBreakTime;
	
	public EventParticipant () {
		
	}
	public EventParticipant(int minimalBreakTime) {
		this.minimalBreakTime = minimalBreakTime;
	}
	public int getMinimalBreakTime() {
		return minimalBreakTime;
	}
	public void setMinimalBreakTime(int minimalBreakTime) {
		this.minimalBreakTime = minimalBreakTime;
	}
}
