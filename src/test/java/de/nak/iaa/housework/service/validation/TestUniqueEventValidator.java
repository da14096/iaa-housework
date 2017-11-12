package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.nak.iaa.housework.model.Building;
import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.Room;
import de.nak.iaa.housework.model.RoomName;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;

@ContextConfiguration(locations = {"classpath:test_spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUniqueEventValidator {
	
	@Mock
	private DomainRepository repository;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidator () {
	
		Lecturer lecturer = new Lecturer();
		
		RoomName name = new RoomName(Building.A, 1);
		Room room = new Room(name);
		
		Event event = new Event();
		event.setStart(LocalDateTime.now());
		event.setEnd(LocalDateTime.now());
		event.setRoom(room);
		event.setLecturer(lecturer);
		
		UniqueEventValidator validator = new UniqueEventValidator(repository);
		
		/* stellt sicher, dass bei keinem gefundenen Raum/Dozenten die Validierung korrekt funktioniert */
		Mockito.when(repository.readAll(Mockito.any())).thenReturn(Collections.emptyList());
		List <Violation> violations = validator.validate(event);
		assertTrue (violations.isEmpty());
		
		/* stellt sicher, dass bei einem gefundenen Dozenten die Validierung fehlschlägt */
		Mockito.when(repository.readAll(Mockito.eq(Event.class), Mockito.any()))
				.thenAnswer(new Answer<List <Event>>() {
					@Override
					public List<Event> answer(InvocationOnMock invocation) throws Throwable {
						PropertyFilterChain chain = (PropertyFilterChain) invocation.getArguments()[1];
						boolean lecturerSearch = 
								chain.getFilters()
										.stream()
										.anyMatch(fc -> lecturer.equals(fc.getFilter().getPropertyValue()));
						return lecturerSearch? Arrays.asList(new Event()): Collections.emptyList();
					}
				});
		violations = validator.validate(event);
		assertFalse (violations.isEmpty());
		
		/* stellt sicher, dass bei einem gefundenen Raum die Validierung fehlschlägt */
		Mockito.when(repository.readAll(Mockito.eq(Event.class), Mockito.any()))
				.thenAnswer(new Answer<List <Event>>() {
					@Override
					public List<Event> answer(InvocationOnMock invocation) throws Throwable {
						PropertyFilterChain chain = (PropertyFilterChain) invocation.getArguments()[1];
						boolean roomSearch = 
								chain.getFilters()
										.stream()
										.anyMatch(fc -> room.equals(fc.getFilter().getPropertyValue()));
						return roomSearch? Arrays.asList(new Event()): Collections.emptyList();
					}
				});
		violations = validator.validate(event);
		assertFalse (violations.isEmpty());
	}
}
