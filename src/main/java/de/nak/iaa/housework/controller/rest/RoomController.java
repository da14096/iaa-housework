package de.nak.iaa.housework.controller.rest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.EventService;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Rest-Schnittstelle, die die Methoden für Räume bereitstellt.
 * 
 * @author Henrik Kriegshammer 6291
 */
@RestController
@RequestMapping("/room")
public class RoomController {

	private final DomainService<Room> roomService;
	private final EventService eventService;
	
	@Autowired
	public RoomController(final DomainService<Room> roomService, EventService eventService) {
		this.roomService = roomService;
		this.eventService = eventService;
	}

	@GetMapping
	public Collection<Room> readAll() {
		return roomService.readAll();
	}
	@PostMapping (path="/create")
	public Room createRoom(@RequestBody final Room room) throws ValidationException {
		return roomService.persist(room);
	}
	/**
	 * Liefert für einen Raum die Ereignisse in der angegebenen Periode
	 * @param room der Raum
	 * @param start der Periode
	 * @param end der Periode
	 * @return die gemappten Veranstaltungen (aufsteigend nach start sortiert)
	 */
	@PostMapping (path="/weekView")
	public Map <LocalDate, List <Event>> getOverview (@RequestBody Room room, 
						@RequestParam (name="start", required=true)@DateTimeFormat(iso=ISO.DATE_TIME) LocalDate start, 
						@RequestParam (name="end", required=true)@DateTimeFormat(iso=ISO.DATE_TIME) LocalDate end) {
		return eventService.getEventsForRoom(room, start, end);
	}
}