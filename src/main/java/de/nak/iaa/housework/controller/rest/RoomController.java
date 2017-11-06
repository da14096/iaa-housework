package de.nak.iaa.housework.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.repository.PropertyFilter;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.ValidationException;

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
  
  @PostMapping (path="/subset")
  public Collection<Room> readRoomsForBuilding(@RequestBody final Building building) {
	  PropertyFilter roomFilter = new PropertyFilter(Operator.EQ, Room.PROPERTY_BUILDING, building);
	  return roomService.readAll(PropertyFilterChain.startWith(roomFilter));
  }
  @PostMapping (path="/create")
  public Room createRoom(@RequestBody final Room room) throws ValidationException {
    return roomService.persist(room);
  }
}