package de.nak.iaa.housework.service.validation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Test;

/**
 * Testet dass die Validierung auf null richtig funktioniert
 * 
 * @author da0015 14096
 */
public class TestNullValidator {

	
	@Test
	public void testValidator () {
		Map <String, Function <Mock, ?>> getter = new HashMap<>();
		getter.put("field1", Mock::getTestField1);
		getter.put("field2", Mock::getTestField2);
		
		PropertyNotNullValidator<Mock> nullValidator = new PropertyNotNullValidator<>(Mock.class, getter);
		Mock mock = new Mock();
		List <Violation> violations = nullValidator.validate(mock);
		assertEquals (2, violations.size());
		
		mock.testField1 = "test";
		violations = nullValidator.validate(mock);
		assertEquals (1, violations.size());
		
		mock.testField2 = "test";
		violations = nullValidator.validate(mock);
		assertEquals (0, violations.size());	
	}
	
	
	private class Mock {
		
		private String testField1;
		private String testField2;
		
		public String getTestField1() {
			return testField1;
		}
		public String getTestField2() {
			return testField2;
		}
		
		
	}
}
