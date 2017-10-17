package de.nordakademie.iaa.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nordakademie.iaa.room.model.Room;
import de.nordakademie.iaa.room.model.RoomRepository;

@Service
public class RoomService {

  private final RoomRepository roomRepository;

  @Autowired
  public RoomService(final RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Transactional(readOnly = true)
  public List<Room> findAll() {
    return roomRepository.findAll();
  }

  @Transactional
  public Room create(final Room room) {
    return roomRepository.create(room);
  }
}
