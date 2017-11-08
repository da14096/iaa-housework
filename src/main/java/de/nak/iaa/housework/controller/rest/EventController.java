package de.nak.iaa.housework.controller.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.RoomService;
import de.nak.iaa.housework.service.ValidationException;

@RestController
@RequestMapping("/event")
public class EventController {

	private final DomainService<Event> eventService;
	private final RoomService roomService;

	@Autowired
	public EventController(final DomainService<Event> eventService, RoomService roomService) {
		this.eventService = eventService;
		this.roomService = roomService;
	}

	@GetMapping
	public Collection<Event> readAll() {
		return eventService.readAll();
	}

	@PostMapping(path = "/availableRooms")
	public List<Room> getAvailableRooms(@RequestBody final Event event) {
		return event == null ? Collections.emptyList(): roomService.getAvailableRooms(event.getStart(), event.getEnd());
	}
	
	@PostMapping(path = "/create")
	public Event createEvent(@RequestBody final Event event) throws ValidationException {
		return eventService.persist(event);
	}
	@PostMapping(path = "/update")
	public Event updateEvent(@RequestBody final Event event) throws ValidationException {
		return eventService.update(event);
	}
	@PostMapping(path = "/delete")
	public void deleteEvent(@RequestBody final Event event) {
		eventService.delete(event);
	}
}
