package de.nak.iaa.housework.controller.rest;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import antlr.debug.Event;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.ValidationException;

@RestController
@RequestMapping("/studentsClass")
public class StudentsClassController {

	private final DomainService<StudentsClass> studentsClassService;

	private static final String JSON_PARAMETER_STUDENTS_CLASS = "studentsClass";
	private static final String JSON_PARAMETER_EVENT = "event";

	@Autowired
	public StudentsClassController(final DomainService<StudentsClass> studentsClassService) {
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

	@PostMapping(path = "/applyStudentsClass")
	public void addEvent(@RequestBody ObjectNode node) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		StudentsClass clazz = mapper.treeToValue(node.get(JSON_PARAMETER_STUDENTS_CLASS), StudentsClass.class);
		Event event = mapper.treeToValue(node.get(JSON_PARAMETER_EVENT), Event.class);
		
		
	}
}
