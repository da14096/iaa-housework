package de.nak.iaa.housework.service.validation;

import java.util.ArrayList;
import java.util.List;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.StudentsClass;

/**
 *  Validiert, dass die Größe von Räumen zu der von Zenturien passen.
 * 
 * @author da0015 14096
 */
@ValidatorBean
public class RoomCapacityValidator extends TypeOrientedValidator<StudentsClass> {

	public RoomCapacityValidator() {
		super (StudentsClass.class);
	}
	
	@Override
	public List<Violation> validate(StudentsClass entity) {
		List <Violation> violations = new ArrayList<>();
		for (Event event: entity.getEventsToAttend()) {
			for (Room room: event.getRooms()) {
				if (entity.getSize() > room.getCapacity()) {
					violations.add(new Violation("Die Größe des Raums [" + room 
							+ "] reicht nicht für die Zenturie [" + entity + "]"));
				}
			}
		}
		return violations;
	}
}
