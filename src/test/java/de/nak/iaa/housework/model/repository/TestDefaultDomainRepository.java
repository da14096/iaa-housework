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

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.EventType;
import de.nak.iaa.housework.model.FieldOfStudy;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.RoomName;
import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.model.StudentsClassId;
import de.nak.iaa.housework.model.repository.PropertyFilter.Operator;
import de.nak.iaa.housework.model.repository.PropertyFilterChain.Connector;


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
		roomName = new RoomName(Building.A, 1);
		room =  new Room(roomName, 30);
		lecturer = new Lecturer("Test", "Lecturer", 15);
		event = new Event(EventType.LECTURE, "TestEvent", START, START.plus(90,ChronoUnit.MINUTES));
		event.addRoom(room);
		event.setLecturer(lecturer);
		event.setChangeDuration(10);
		
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
	public void testForEvent ()  {
		List <Event> allEvents = repository.readAll(Event.class);
		assertTrue (allEvents.isEmpty());
		assertNull (repository.find(Event.class, 1L));
		
		assertNull (event.getId());
		Event updatedEvent = repository.create(event);
		assertNotNull (event.getId());
		assertNotNull (repository.find(Event.class, 1L));
		
		/* consistent */
		assertEquals (event, updatedEvent);
		assertEquals (event.getType(), updatedEvent.getType());
		assertEquals (event.getTitle(), updatedEvent.getTitle());
		assertEquals (event.getStart(), updatedEvent.getStart());
		assertEquals (event.getEnd(), updatedEvent.getEnd());
		assertEquals (event.getRooms(), updatedEvent.getRooms());
		assertEquals (event.getLecturer(), updatedEvent.getLecturer());
		assertEquals (event.getChangeDuration(), updatedEvent.getChangeDuration());
		
		repository.create(lecturer);
		repository.create(room);
		
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
		
			
		/* Test that the relevant filter-operations work correctly */
		// 1.) find Events in dateRange
		PropertyFilter fromFilter = new PropertyFilter(START.minusDays(5), Operator.LESSEQ, Event.PROPERTY_NAME_START);
		PropertyFilter toFilter = new PropertyFilter(START.plusDays(5), Operator.GREATEREQ, Event.PROPERTY_NAME_START);
		PropertyFilterChain chain = PropertyFilterChain.startWith(fromFilter).appendFilter(toFilter, Connector.AND);
		
		allEvents = repository.readAll(Event.class, chain);
		assertFalse (allEvents.isEmpty());
		assertEquals (allEvents.get(0), event);
		
		// 1.1.) Counter-example
		fromFilter = new PropertyFilter(START.minusDays(5), Operator.GREATEREQ, Event.PROPERTY_NAME_START);
		toFilter = new PropertyFilter(START.plusDays(5), Operator.LESSEQ, Event.PROPERTY_NAME_START);
		chain = PropertyFilterChain.startWith(fromFilter).appendFilter(toFilter, Connector.AND);
		
		allEvents = repository.readAll(Event.class, chain);
		assertTrue (allEvents.isEmpty());
			
		
		// 3.) find Event by lecturer
		PropertyFilter lecturerFilter = new PropertyFilter(lecturer, Operator.EQ, Event.PROPERTY_NAME_LECTURER);
		chain = PropertyFilterChain.startWith(lecturerFilter);
		
		allEvents = repository.readAll(Event.class, chain);
		assertFalse (allEvents.isEmpty());
		assertEquals (event, allEvents.get(0));
		
		// 3.1) Counter-example
		lecturerFilter = new PropertyFilter(lecturer, Operator.NOTEQ, Event.PROPERTY_NAME_LECTURER);
		chain = PropertyFilterChain.startWith(lecturerFilter);
		
		allEvents = repository.readAll(Event.class, chain);
		assertTrue (allEvents.isEmpty());
		
		
		
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
		Lecturer updatedLecturer = repository.create(lecturer);
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
		Room updatedRoom = repository.create(room);
		
		/* consistent */
		assertEquals (room, updatedRoom);
		assertEquals (room.getName(), updatedRoom.getName());
		assertEquals (room.getCapacity(), updatedRoom.getCapacity());
				
		allRoom = repository.readAll(Room.class);
		assertFalse (allRoom.isEmpty());
		Room savedRoom = allRoom.get(0);
		
		assertEquals (repository.find(Room.class, roomName), savedRoom);
		
		PropertyFilter roomFilter = new PropertyFilter(Building.A, Operator.EQ, Room.PROPERTY_BUILDING);
		List <Room> matchingRooms = repository.readAll(Room.class, PropertyFilterChain.startWith(roomFilter));
		assertFalse (matchingRooms.isEmpty());
		
		roomFilter = new PropertyFilter(Building.B, Operator.EQ, Room.PROPERTY_BUILDING);
		matchingRooms = repository.readAll(Room.class, PropertyFilterChain.startWith(roomFilter));
		assertTrue (matchingRooms.isEmpty());
		
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
		StudentsClass updatedStudentsClass = repository.create(studentsClass);
		
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
