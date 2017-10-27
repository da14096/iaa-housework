package de.nak.iaa.housework.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.service.AlreadyExistsException;
import de.nak.iaa.housework.service.DomainService;

@RestController
@RequestMapping("/room")
public class RoomController {

  private final DomainService<Room> roomService;

  @Autowired
  public RoomController(final DomainService<Room> roomService) {
    this.roomService = roomService;
  }

  @GetMapping
  public Collection<Room> readAll() {
    return roomService.readAll();
  }
  @PostMapping
  public Room createRoom(@RequestBody final Room room) throws AlreadyExistsException {
    return roomService.persist(room);
  }
}