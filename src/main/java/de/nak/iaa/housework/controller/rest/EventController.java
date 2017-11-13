package de.nak.iaa.housework.controller.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.EventService;
import de.nak.iaa.housework.service.RoomService;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Rest-Schnittstelle, die die Methoden für Veranstaltungen bereitstellt.
 * 
 * @author Henrik Kriegshammer 6291
 */
@RestController
@RequestMapping("/event")
public class EventController {

	private final EventService eventService;
	private final RoomService roomService;

	@Autowired
	public EventController(final EventService eventService, RoomService roomService) {
		this.eventService = eventService;
		this.roomService = roomService;
	}

	@GetMapping
	public Collection<Event> readAll() {
		return eventService.readAll();
	}

	/**
	 * Liefert zu einem Ereignis alle noch nicht belegten Räume
	 * @param event das Ereignis
	 * @return verfügbare Räume
	 */
	@PostMapping(path = "/availableRooms")
	public List<Room> getAvailableRooms(@RequestBody final Event event) {
		return event == null ? Collections.emptyList(): roomService.getAvailableRooms(event.getStart(), event.getEnd());
	}
	/**
	 * Speichert ein Ereignis über die Parameter weeks und validate kann gesteuert werden ob die Veranstaltung für
	 * mehrere Wochen wiederholt werden soll und ob eine Validierung stattfinden soll.
	 * @param event die Veranstaltung 
	 * @param weeks Wiederholung
	 * @param validate Validierung
	 * @return Ereignisse
	 * @throws ValidationException im Falle von fehlgeschlagenen Validierungen
	 */
	@PostMapping(path = "/save")
	public List <Event> saveEvent (@RequestBody Event event, 
									@RequestParam(name="weeks", defaultValue="0") int weeks,
									@RequestParam(name="validate", defaultValue="true") boolean validate) 
											throws ValidationException {
		return eventService.saveEvent(event, weeks, validate);
	}
	/**
	 * Löscht eine Veranstaltung
	 * @param event
	 */
	@PostMapping(path = "/delete")
	public void deleteEvent(@RequestBody final Event event) {
		eventService.delete(event);
	}
	/**
	 * Liefert zu einer Veranstaltung die referenzierenden Zenturien (die die dran teilnehmen)
	 * @param event
	 * @return
	 */
	@PostMapping(path = "/studentsClasses")
	public List <StudentsClass> getAssignedStudentsClasses (@RequestBody Event event) {
		List <StudentsClass> result = eventService.getAssignedStudentsClasses(event);
		return result;		
	}
}
