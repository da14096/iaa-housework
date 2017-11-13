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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.StudentsClassService;
import de.nak.iaa.housework.service.validation.ValidationException;

/**
 * Rest-Schnittstelle, die die Methoden für Zenturien bereitstellt.
 * 
 * @author Henrik Kriegshammer 6291
 */
@RestController
@RequestMapping("/studentsClass")
public class StudentsClassController {

	private static final ObjectMapper OBJECT_MAPPER = getObjectMapper();
	
	private final StudentsClassService studentsClassService;
	
	private static final String JSON_PARAMETER_STUDENTS_CLASS = "studentsClass";
	private static final String JSON_PARAMETER_EVENT = "event";

	@Autowired
	public StudentsClassController(final StudentsClassService studentsClassService) {
		this.studentsClassService = studentsClassService;
	}

	@GetMapping
	public Collection<StudentsClass> readAll() {
		List<StudentsClass> all = studentsClassService.readAll();
		return all;
	}

	@PostMapping(path = "/create")
	public StudentsClass create(@RequestBody final StudentsClass studentsClass) throws ValidationException {
		return studentsClassService.persist(studentsClass);
	}
	/**
	 * Liefert für eine Zenturie die Ereignisse in der angegebenen Periode
	 * @param studentsClass die Zenturie
	 * @param start der Periode
	 * @param end der Periode
	 * @return die gemappten Veranstaltungen (aufsteigend nach start sortiert)
	 */
	@PostMapping (path="/weekView")
	public Map <LocalDate, List <Event>> getOverview (@RequestBody StudentsClass studentsClass, 
						@RequestParam (name="start", required=true)@DateTimeFormat(iso=ISO.DATE_TIME) LocalDate start, 
						@RequestParam (name="end", required=true)@DateTimeFormat(iso=ISO.DATE_TIME) LocalDate end) {
		return studentsClassService.resolveEventsMapped(studentsClass, start, end);
	}
	/**
	 * Speichert ein Ereignis über die Parameter weeks und validate kann gesteuert werden ob die Veranstaltung für
	 * mehrere Wochen wiederholt werden soll und ob eine Validierung stattfinden soll. Daraufhin werden die Ereignisse 
	 * der Zenturie zugeordnet.
	 * 
	 * @param node reqBody
	 * @param validate validierung
	 * @param weeks wiederholung in Wochen
	 * @return die Ereignisse
	 * @throws JsonProcessingException
	 * @throws ValidationException bei Validierungs-Fehlschlägen
	 */
	@PostMapping(path = "/applyEvent")
	public List <Event> addEvent(@RequestBody ObjectNode node, 
						@RequestParam(name="validate", defaultValue="true") boolean validate,
						@RequestParam(name="weeks", defaultValue="0") int weeks) 
								throws JsonProcessingException, ValidationException {
		
		StudentsClass clazz = OBJECT_MAPPER.treeToValue(node.get(JSON_PARAMETER_STUDENTS_CLASS), StudentsClass.class);
		Event event = OBJECT_MAPPER.treeToValue(node.get(JSON_PARAMETER_EVENT), Event.class);
		return studentsClassService.addEvent(clazz, event, validate, weeks);
	}
	/**
	 * Löscht ein Ereignis von einer Zenturie
	 * 
	 * @param node reqBody
	 * @throws JsonProcessingException
	 * @throws ValidationException bei Validierungsfehlschlägen
	 */
	@PostMapping(path = "/removeEvent")
	public void removeEvent(@RequestBody ObjectNode node) throws JsonProcessingException, ValidationException {
		StudentsClass clazz = OBJECT_MAPPER.treeToValue(node.get(JSON_PARAMETER_STUDENTS_CLASS), StudentsClass.class);
		Event event = OBJECT_MAPPER.treeToValue(node.get(JSON_PARAMETER_EVENT), Event.class);
		studentsClassService.removeEvent(clazz, event);
	}
	
	private static ObjectMapper getObjectMapper () {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
