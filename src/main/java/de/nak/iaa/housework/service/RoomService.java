package de.nak.iaa.housework.service;

import java.time.LocalDateTime;
import java.util.List;

import de.nak.iaa.housework.model.Room;

public interface RoomService extends DomainService<Room> {

	List <Room> getAvailableRooms (LocalDateTime start, LocalDateTime end);
}
