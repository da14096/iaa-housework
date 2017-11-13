package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.StudentsClassId;

/**
 * Testet den Validator, welcher Überschneidungen sowie Nicht-Einhaltung von Zenturienpausenzeiten erkennt-.
 * 
 * @see {@link StudentsClassEventsValidator}
 * @author da0015
 */
public class TestStudentsClassEventsValidator {
	
	@Test
	public void testValidator () throws Exception {
		Event event = TestUtils.getEventWithIdForTest(0);
		event.setTitle("Test");
		event.setType(EventType.LECTURE);
		event.setStart(LocalDateTime.of(2017, 11, 6, 10, 30));
		event.setEnd(LocalDateTime.of(2017, 11, 6, 11, 30));
		Event event1 = TestUtils.getEventWithIdForTest(1);
		event1.setTitle("Test1");
		event1.setType(EventType.LECTURE);
		event1.setStart(LocalDateTime.of(2017, 11, 6, 12, 30));
		event1.setEnd(LocalDateTime.of(2017, 11, 6, 13, 30));
		Event event2 = TestUtils.getEventWithIdForTest(2);
		event2.setTitle("Test2");
		event2.setType(EventType.LECTURE);
		event2.setStart(LocalDateTime.of(2017, 11, 6, 13, 30));
		event2.setEnd(LocalDateTime.of(2017, 11, 6, 15, 30));
		
		StudentsClassId id = new StudentsClassId(FieldOfStudy.I, 2014, 'c');
		StudentsClass clazz = new StudentsClass(id);
		clazz.addEvent(event);
		clazz.addEvent(event1);
		clazz.addEvent(event2);
		
		StudentsClassEventsValidator validator = new StudentsClassEventsValidator();
		List <Violation> violations = validator.validate(clazz);
		assertTrue (violations.isEmpty());
		
		clazz.setMinimalBreakTime(45);
		violations = validator.validate(clazz);
		assertFalse (violations.isEmpty());
		assertEquals (1, violations.size());
		
		clazz.setMinimalBreakTime(90);
		violations = validator.validate(clazz);
		assertFalse (violations.isEmpty());
		assertEquals (2, violations.size());
		/* clear to assert cutting events in the next step */
		clazz.setMinimalBreakTime(0);
		
		event1.setStart(LocalDateTime.of(2017, 11, 6, 11, 25));
		violations = validator.validate(clazz);
		assertFalse (violations.isEmpty());
		assertEquals (1, violations.size());
		
		event1.setEnd(LocalDateTime.of(2017, 11, 6, 13, 40));
		violations = validator.validate(clazz);
		assertFalse (violations.isEmpty());
		assertEquals (2, violations.size());
	}
}
