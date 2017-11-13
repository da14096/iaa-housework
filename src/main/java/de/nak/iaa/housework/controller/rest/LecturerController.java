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
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.EventService;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Rest-Schnittstelle, die die Methoden für Dozenten bereitstellt.
 * 
 * @author Henrik Kriegshammer 6291
 */
@RestController
@RequestMapping("/lecturer")
public class LecturerController {

	private final DomainService<Lecturer> lecturerService;
	private final EventService eventService;

	@Autowired
	public LecturerController(final DomainService<Lecturer> lecturerService, EventService eventService) {
		this.lecturerService = lecturerService;
		this.eventService = eventService;
	}

	@GetMapping
	public Collection<Lecturer> readAll() {
		return lecturerService.readAll();
	}

	@PostMapping(path = "/create")
	public Lecturer create(@RequestBody final Lecturer lecturer) throws ValidationException {
		return lecturerService.persist(lecturer);
	}

	/**
	 * Liefert für einen Dozenten die Ereignisse in der angegebenen Periode
	 * @param lecturer der Dozent
	 * @param start der Periode
	 * @param end der Periode
	 * @return die gemappten Veranstaltungen (aufsteigend nach start sortiert)
	 */
	@PostMapping(path = "/weekView")
	public Map<LocalDate, List<Event>> getOverview(@RequestBody Lecturer lecturer,
			@RequestParam(name = "start", required=true) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate start,
			@RequestParam(name = "end", required=true) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate end) {
		return eventService.getEventsForLecturer(lecturer, start, end);
	}
}
