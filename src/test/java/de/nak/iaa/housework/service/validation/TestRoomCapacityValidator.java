package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.RoomName;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.StudentsClassId;

public class TestRoomCapacityValidator {
	
	@Test
	public void testValidator () {
		RoomName name = new RoomName(Building.A, 1);
		Room room = new Room(name, 20);
		
		Event event = new Event(EventType.LECTURE, "Test");
		event.setRoom(room);
		
		StudentsClassId id = new StudentsClassId(FieldOfStudy.I, 14, 'c');
		StudentsClass clazz = new StudentsClass(id);
		clazz.setSize(30);
		clazz.addEvent(event);
		
		RoomCapacityValidator validator = new RoomCapacityValidator();
		List <Violation> violations = validator.validate(clazz);
		assertFalse (violations.isEmpty());
		
		room.setCapacity(35);
		violations = validator.validate(clazz);
		assertTrue (violations.isEmpty());
	}
}
