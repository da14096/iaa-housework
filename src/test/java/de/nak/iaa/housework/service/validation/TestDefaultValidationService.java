package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Stellt sicher, dass die der {@link DefaultValidationService} die {@link Validator}, die r übergeben bekommt richtig
 * aufruft. Hierbei wird besonders die Berücksichtigung der Annotation {@link ValidatorBean} beachtet
 * 
 * @author da0015 14096
 */
@ContextConfiguration(classes = TestDefaultValidationService.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDefaultValidationService {

	private SimpleValidator simpleValidator = new SimpleValidator();
	private StartingValidator startingValidator = new StartingValidator();
	private SuccessingValidator successingValidator = new SuccessingValidator();
	
	private DefaultValidationService validationService;
	
	@Before
	public void before () {
		List <Validator<?>> validators = Arrays.asList(simpleValidator, startingValidator, successingValidator);
		this.validationService = new DefaultValidationService(validators);
	}
	
	@Test
	public void testValidation () {
		Object objToValidate = new Object();
		Set <Violation> violations = validationService.validate(objToValidate);
		assertTrue (violations.isEmpty());
		assertEquals (1, simpleValidator.validationInvocations);
		assertEquals (1, startingValidator.validationInvocations);
		assertEquals (1, successingValidator.validationInvocations);
		
		startingValidator.valid = false;
		violations = validationService.validate(objToValidate);
		assertFalse (violations.isEmpty());
		assertEquals (2, simpleValidator.validationInvocations);
		assertEquals (2, startingValidator.validationInvocations);
		assertEquals (1, successingValidator.validationInvocations);
		
		simpleValidator.invalid = true;
		violations = validationService.validate(objToValidate);
		assertFalse (violations.isEmpty());
		assertEquals (3, simpleValidator.validationInvocations);
		assertEquals (3, startingValidator.validationInvocations);
		assertEquals (1, successingValidator.validationInvocations);
		
		startingValidator.valid = true;
		violations = validationService.validate(objToValidate);
		assertFalse (violations.isEmpty());
		assertEquals (4, simpleValidator.validationInvocations);
		assertEquals (4, startingValidator.validationInvocations);
		assertEquals (2, successingValidator.validationInvocations);
	}
	
	@ValidatorBean
	private class StartingValidator implements Validator<Object> {
		boolean valid = true;
		int validationInvocations = 0;
		@Override
		public boolean isResponsible(Class<?> type) {
			return true;
		}
		@Override
		public List<Violation> validate(Object entity) {
			validationInvocations++;
			return valid? Collections.emptyList(): Arrays.asList(new Violation("Test"));
		}
	}
	@ValidatorBean(previentValidator=StartingValidator.class)
	private class SuccessingValidator implements Validator<Object> {
		int validationInvocations = 0;
		@Override
		public boolean isResponsible(Class<?> type) {
			return true;
		}
		@Override
		public List<Violation> validate(Object entity) {
			validationInvocations++;	
			return Collections.emptyList();
		}
	}
	@ValidatorBean
	private class SimpleValidator implements Validator<Object> {
		boolean invalid = false;
		int validationInvocations = 0;
		@Override
		public boolean isResponsible(Class<?> type) {
			return true;
		}
		@Override
		public List<Violation> validate(Object entity) {
			validationInvocations++;	
			return invalid? Arrays.asList(new Violation("Test")): Collections.emptyList();
		}
	}
}
