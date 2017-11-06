package de.nak.iaa.housework.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;

@RestController
@RequestMapping("/model")
public class ModelController {
	
	@GetMapping(path="/buildings")
	public Building[] readBuildings() {
		return Building.values();
	}
	@GetMapping(path="/fieldsOfStudy")
	public FieldOfStudy[] readFieldsOfStudy() {
		return FieldOfStudy.values();
	}
	@GetMapping(path="/eventTypes")
	public EventType[] readEventTypes() {
		return EventType.values();
	}
}