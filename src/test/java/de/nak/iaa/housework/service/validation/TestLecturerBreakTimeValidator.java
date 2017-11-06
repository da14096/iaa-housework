package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.model.repository.DomainRepository;
import de.nak.iaa.housework.model.repository.PropertyFilterChain;

@ContextConfiguration(locations = {"classpath:test_spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLecturerBreakTimeValidator {
	
	@Mock
	private DomainRepository repository;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidator () {
		Lecturer lecturer = new Lecturer("Test", "SurTest", 30);
		
		Event event = new Event();
		event.setLecturer(lecturer);
		LocalDateTime start = LocalDateTime.now();
		event.setStart(start);
		
		LecturerBreakTimeValidator validator = new LecturerBreakTimeValidator(repository);
		Mockito.when(repository.readAll(Mockito.eq(Event.class), Mockito.any()))
				.thenAnswer(new Answer<List <Event>>() {
					@Override
					public List<Event> answer(InvocationOnMock invocation) throws Throwable {
						PropertyFilterChain filter = (PropertyFilterChain) invocation.getArguments()[1];
						LocalDateTime maxPrevEnd = start.minus(30, ChronoUnit.MINUTES);
						assertTrue(filter.getFilters()
							.stream()
							.filter(f -> Event.PROPERTY_NAME_END.equals(f.getFilter().getPropertyName()))
							.anyMatch(f -> maxPrevEnd.equals(f.getFilter().getPropertyValue())));
						return Collections.emptyList();
					}
				});
		List <Violation> violations = validator.validate(event);
		assertTrue (violations.isEmpty());
		
		Mockito.when(repository.readAll(Mockito.eq(Event.class), Mockito.any())).thenReturn(Arrays.asList(new Event()));
		violations = validator.validate(event);
		assertFalse (violations.isEmpty());	
	}
}
