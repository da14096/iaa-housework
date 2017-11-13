package de.nak.iaa.housework.service.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.RoomType;

/**
 * Dieser Validator validiert die Konsistenz einer Raum-Entität.
 * Hier wird beispielsweise die Mindest-Wechselzeit eines Computerraumes geprüft.
 * 
 * @author David Aldrup 14096
 */
@ValidatorBean
public class RoomPropertyValidator extends PropertyNotNullValidator<Room> {

	public RoomPropertyValidator() {
		super(Room.class, getPropertiesToValidate());
	}
	
	@Override
	protected Violation validate(Room entity, String propertyName) {
		switch (propertyName) {
		case Room.PROPERTY_CHANGE_DURATION:
			if (entity.getType() == RoomType.COMPUTER && entity.getChangeDuration() < 15) {
				return new Violation("Computerräume haben eine Mindes-Wechselzeit von 15 Minuten.");
			} else {
				return null;
			}
		case Room.PROPERTY_CAPACITY:
			if (entity.getCapacity() < 1) {
				return new Violation("Räume müssen mindestens Platz für eine Person bieten.");
			}
		default:
			return super.validate(entity, propertyName);
		}
	}
	
	private static Map<String, Function<Room, ?>> getPropertiesToValidate() {
		Map <String, Function<Room, ?>> result = new HashMap<>();
		result.put(Room.PROPERTY_BUILDING, Room::getBuilding);
		result.put(Room.PROPERTY_ROOM_NUMBER, Room::getRoomNumber);
		result.put(Room.PROPERTY_TYPE, Room::getType);
		result.put(Room.PROPERTY_CAPACITY, Room::getCapacity);
		result.put(Room.PROPERTY_CHANGE_DURATION, Room::getChangeDuration);
		return result;
	}
}
