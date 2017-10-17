package de.nordakademie.iaa.room.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Room create(final Room room) {
    entityManager.persist(room);
    return room;
  }

  public List<Room> findAll() {
    return entityManager.createQuery("SELECT r FROM Room r", Room.class).getResultList();
  }

  public Room findOne(final long roomId) {
    return entityManager.find(Room.class, roomId);
  }
}
