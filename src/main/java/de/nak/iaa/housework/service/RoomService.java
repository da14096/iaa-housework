package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.List;

import de.nak.iaa.housework.model.Room;

/**
 * Spezialisierung des allgemeinen {@link DomainService}. Angereichtert um Raum-spezifische Methoden.
 * 
 * @author da0015 14096
 *
 */
public interface RoomService extends DomainService<Room> {

	/**
	 * Liefert die noch nicht belegten Räume in einem Intervall
	 * 
	 * @param start des Intervalls
	 * @param end des Intervalls
	 * @return die verfügbaren Räume
	 */
	List <Room> getAvailableRooms (LocalDateTime start, LocalDateTime end);
}
