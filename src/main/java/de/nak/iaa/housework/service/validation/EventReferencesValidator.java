package de.nak.iaa.housework.service.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.nak.iaa.housework.model.Event;

@ValidatorBean
public class EventReferencesValidator extends PropertyValidator<Event> {

	public EventReferencesValidator() {
		super(Event.class, getPropertiesToValidate());
	}
	
	private static Map<String, Function<Event, ?>> getPropertiesToValidate() {
		Map <String, Function<Event, ?>> result = new HashMap<>();
		result.put(Event.PROPERTY_NAME_EVENT_TYPE, Event::getType);
		result.put(Event.PROPERTY_NAME_TITLE, Event::getTitle);
		result.put(Event.PROPERTY_NAME_START, Event::getStart);
		result.put(Event.PROPERTY_NAME_END, Event::getEnd);
		result.put(Event.PROPERTY_NAME_CHANGE_DURATION, Event::getChangeDuration);
		result.put(Event.PROPERTY_NAME_ROOM, Event::getRoom);
		result.put(Event.PROPERTY_NAME_LECTURER, Event::getLecturer);
		return result;
	}
}
