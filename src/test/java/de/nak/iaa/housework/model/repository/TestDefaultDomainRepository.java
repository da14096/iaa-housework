package de.nak.iaa.housework.model.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.RoomName;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.StudentsClassId;


/**
 * Diese Test-Klasse enthält die Tests um zu überprüfen, dass das {@link DefaultDomainRepository}
 * für alle verwendeten Entitätstypen korrekt funktioniert.
 * 
 * @author Nico Kriebel
 */
@ContextConfiguration(locations = {"classpath:test_spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDefaultDomainRepository {

	private static final LocalDateTime START = LocalDateTime.now();
	
	@Autowired
	private DefaultDomainRepository repository;
	
	private Lecturer lecturer;
	private Event event;
	
	private RoomName roomName;
	private Room room;
	
	private StudentsClassId studentsClassId;
	private StudentsClass studentsClass;
	
	/** setup routine um Datenobjekte zu haben */
	@Before
	public void setup () {
		roomName = new RoomName('A', 1);
		room =  new Room(roomName, 30);
		lecturer = new Lecturer("TestLecturer", 15);
		event = new Event(EventType.LECTURE, "TestEvent", START, START.plus(90,ChronoUnit.MINUTES), room, lecturer, 10);
		
		studentsClassId = new StudentsClassId(FieldOfStudy.I, 14, 'c');
		studentsClass = new StudentsClass(studentsClassId, 36, 15);
	}
	
	/**
	 * Tested das korrekte Verhalten im Bezug auf {@link Event}
	 * Prüft hierbei ob:
	 * 1.) {@link DefaultDomainRepository#readAll(Class)} korrekt funktioniert
	 * 2.) {@link DefaultDomainRepository#find(Class, Object)} korrekt funktioniert
	 * 3.) {@link DefaultDomainRepository#update(Object)} korrekt funktioniert und die Objekte hierbei konsistent sind
	 * 4.) {@link DefaultDomainRepository#delete(Object)} korrekt funktioniert
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testForEvent () {
		List <Event> allEvents = repository.readAll(Event.class);
		assertTrue (allEvents.isEmpty());
		assertNull (repository.find(Event.class, 1L));
		
		assertNull (event.getId());
		Event updatedEvent = repository.update(event);
		assertNotNull (event.getId());
		assertNotNull (repository.find(Event.class, 1L));
		
		/* consistent */
		assertEquals (event, updatedEvent);
		assertEquals (event.getType(), updatedEvent.getType());
		assertEquals (event.getTitle(), updatedEvent.getTitle());
		assertEquals (event.getStart(), updatedEvent.getStart());
		assertEquals (event.getEnd(), updatedEvent.getEnd());
		assertEquals (event.getRoom(), updatedEvent.getRoom());
		assertEquals (event.getLecturer(), updatedEvent.getLecturer());
		assertEquals (event.getChangeDuration(), updatedEvent.getChangeDuration());
		
		repository.update(lecturer);
		repository.update(room);
		
		allEvents = repository.readAll(Event.class);
		assertFalse (allEvents.isEmpty());
		Event savedEvent = allEvents.get(0);
		
		assertEquals (savedEvent, updatedEvent);
		event.setTitle("AnotherTitle");
		repository.update(event);
		
		allEvents = repository.readAll(Event.class);
		assertFalse (allEvents.isEmpty());
		savedEvent = allEvents.get(0);
		assertEquals ("AnotherTitle", savedEvent.getTitle());
		
		repository.delete(event);
		allEvents = repository.readAll(Event.class);
		assertTrue (allEvents.isEmpty());
		assertNull (repository.find(Event.class, 1L));
	}
	
	/**
	 * Tested das korrekte Verhalten im Bezug auf {@link Lecturer}
	 * Prüft hierbei ob:
	 * 1.) {@link DefaultDomainRepository#readAll(Class)} korrekt funktioniert
	 * 2.) {@link DefaultDomainRepository#find(Class, Object)} korrekt funktioniert
	 * 3.) {@link DefaultDomainRepository#update(Object)} korrekt funktioniert und die Objekte hierbei konsistent sind
	 * 4.) {@link DefaultDomainRepository#delete(Object)} korrekt funktioniert
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testForLecturer () {
		List <Lecturer> allLecturer = repository.readAll(Lecturer.class);
		assertTrue (allLecturer.isEmpty());
		assertNull (repository.find(Lecturer.class, 1L));
		
		assertNull (lecturer.getPersonnelNumber());
		Lecturer updatedLecturer = repository.update(lecturer);
		assertNotNull (lecturer.getPersonnelNumber());
		assertNotNull (repository.find(Lecturer.class, 1L));
		
		/* consistent */
		assertEquals (lecturer, updatedLecturer);
		assertEquals (lecturer.getName(), updatedLecturer.getName());
		assertEquals (lecturer.getMinimalBreakTime(), updatedLecturer.getMinimalBreakTime());
		
		allLecturer = repository.readAll(Lecturer.class);
		assertFalse (allLecturer.isEmpty());
		Lecturer savedLecturer = allLecturer.get(0);
		
		assertEquals (savedLecturer, updatedLecturer);
		lecturer.setName("AnotherName");
		repository.update(lecturer);
		
		allLecturer = repository.readAll(Lecturer.class);
		assertFalse (allLecturer.isEmpty());
		savedLecturer = allLecturer.get(0);
		assertEquals ("AnotherName", savedLecturer.getName());
		
		repository.delete(lecturer);
		allLecturer = repository.readAll(Lecturer.class);
		assertTrue (allLecturer.isEmpty());
		assertNull (repository.find(Lecturer.class, 1L));
	}
	
	/**
	 * Tested das korrekte Verhalten im Bezug auf {@link Room}
	 * Prüft hierbei ob:
	 * 1.) {@link DefaultDomainRepository#readAll(Class)} korrekt funktioniert
	 * 2.) {@link DefaultDomainRepository#find(Class, Object)} korrekt funktioniert
	 * 3.) {@link DefaultDomainRepository#update(Object)} korrekt funktioniert und die Objekte hierbei konsistent sind
	 * 4.) {@link DefaultDomainRepository#delete(Object)} korrekt funktioniert
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testForRoom () {
		List <Room> allRoom = repository.readAll(Room.class);
		assertTrue (allRoom.isEmpty());
		
		assertNull (repository.find(Room.class, roomName));
		
		assertNotNull (room.getName());
		Room updatedRoom = repository.update(room);
		
		/* consistent */
		assertEquals (room, updatedRoom);
		assertEquals (room.getName(), updatedRoom.getName());
		assertEquals (room.getCapacity(), updatedRoom.getCapacity());
				
		allRoom = repository.readAll(Room.class);
		assertFalse (allRoom.isEmpty());
		Room savedRoom = allRoom.get(0);
		
		assertEquals (repository.find(Room.class, roomName), savedRoom);
		
		assertEquals (savedRoom, updatedRoom);
		room.setCapacity(50);
		repository.update(room);
		
		allRoom = repository.readAll(Room.class);
		assertFalse (allRoom.isEmpty());
		savedRoom = allRoom.get(0);
		assertEquals (Integer.valueOf(50), savedRoom.getCapacity());
		
		repository.delete(room);
		allRoom = repository.readAll(Room.class);
		assertTrue (allRoom.isEmpty());
		assertNull (repository.find(Room.class, roomName));
	}
	
	/**
	 * Tested das korrekte Verhalten im Bezug auf {@link StudentsClass}
	 * Prüft hierbei ob:
	 * 1.) {@link DefaultDomainRepository#readAll(Class)} korrekt funktioniert
	 * 2.) {@link DefaultDomainRepository#find(Class, Object)} korrekt funktioniert
	 * 3.) {@link DefaultDomainRepository#update(Object)} korrekt funktioniert und die Objekte hierbei konsistent sind
	 * 4.) {@link DefaultDomainRepository#delete(Object)} korrekt funktioniert
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testForStudentsClass () {
		List <StudentsClass> allStudentsClass = repository.readAll(StudentsClass.class);
		assertTrue (allStudentsClass.isEmpty());
		
		assertNull (repository.find(StudentsClass.class, studentsClassId));
		
		assertNotNull (studentsClass.getName());
		StudentsClass updatedStudentsClass = repository.update(studentsClass);
		
		/* consistent */
		assertEquals (studentsClass, updatedStudentsClass);
		assertEquals (studentsClass.getName(), updatedStudentsClass.getName());
		assertEquals (studentsClass.getSize(), updatedStudentsClass.getSize());
				
		allStudentsClass = repository.readAll(StudentsClass.class);
		assertFalse (allStudentsClass.isEmpty());
		StudentsClass savedStudentsClass = allStudentsClass.get(0);
		
		assertEquals (repository.find(StudentsClass.class, studentsClassId), savedStudentsClass);
		
		assertEquals (savedStudentsClass, updatedStudentsClass);
		studentsClass.setSize(50);
		repository.update(studentsClass);
		
		allStudentsClass = repository.readAll(StudentsClass.class);
		assertFalse (allStudentsClass.isEmpty());
		savedStudentsClass = allStudentsClass.get(0);
		assertEquals (50, savedStudentsClass.getSize());
		
		repository.delete(studentsClass);
		allStudentsClass = repository.readAll(StudentsClass.class);
		assertTrue (allStudentsClass.isEmpty());
		assertNull (repository.find(StudentsClass.class, studentsClassId));
	}
}
