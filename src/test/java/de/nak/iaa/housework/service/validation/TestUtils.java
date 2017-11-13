package de.nak.iaa.housework.service.validation;

import java.lang.reflect.Field;

import de.nak.iaa.housework.model.Event;

class TestUtils {

	public static final Event getEventWithIdForTest (long id) throws Exception {
		Event event = new Event();
		Field idField = Event.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(event, id);
		return event;
	}
}
