package de.nordakademie.iaa.room.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nordakademie.iaa.room.model.Room;
import de.nordakademie.iaa.room.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  @Autowired
  public RoomController(final RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping
  public List<Room> findAll() {
    return roomService.findAll();
  }

  @PostMapping
  public Room saveRoom(@RequestBody final Room room) {
    return roomService.create(room);
  }
}
