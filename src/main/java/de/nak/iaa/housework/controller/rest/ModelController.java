package de.nak.iaa.housework.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;
import de.nak.iaa.housework.model.RoomType;

/**
 * EInfacher Rest-Controller, der die vorhandenen Enums bereitstellt
 * 
 * @author Tim Lindemann 6436
 */
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
	@GetMapping(path="/roomTypes")
	public RoomType[] readRoomTypes() {
		return RoomType.values();
	}
}
