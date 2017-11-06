package de.nak.iaa.housework.service.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultValidationService implements ValidationService {

	private final List <Validator<?>> validators;
	private final Map <Class, ValidatorQueue> resolvedValidatorQueues = new HashMap<>();
	
	@Autowired(required=false)
	public DefaultValidationService(List <Validator<?>> validators) {
		this.validators = validators;
	}
	
	@Override
	public <ET> List<Violation> validate(ET entity) {
		ValidatorQueue queue = getOrCreateValidatorQueue(entity);
		
		List <Violation> violations = new ArrayList<>();
		List <Validator> validators = new ArrayList<>(queue.getStartingValidators());
		while (!validators.isEmpty()) {
			Validator validator = validators.remove(0);
			List <Violation> additionalViolations = validator.validate(entity);
			/* if the validation failed the successing validators will not perform */
			if (additionalViolations == null || additionalViolations.isEmpty()) {
				validators.addAll(queue.getSuccessingValidators(validator));
			} else {
				violations.addAll(additionalViolations);
			}
		}
		return violations;				
	}
	
	private <ET> ValidatorQueue getOrCreateValidatorQueue (ET entity) {
		Class <?> entityType = entity.getClass();
		ValidatorQueue validatorQueue = resolvedValidatorQueues.get(entityType);
		if (validatorQueue == null) {
			/* first resolve all validators that want to validate the entity*/
			List <Validator> responsibleValidators = validators.stream()
										.filter(validator -> validator.isResponsible(entity.getClass()))
										.collect(Collectors.toList());
			/* then build a queue depending on the meta-information (e.g. to ensure that all references are correctly
			 * set on the entity and you don't have to multiply make null-reference checks */
			validatorQueue = ValidatorQueue.build(responsibleValidators);
			resolvedValidatorQueues.put(entityType, validatorQueue);
		} 
		return validatorQueue;
	}
	
	
	private static class ValidatorQueue {
		
		/* explizited Mapping der Validatoren zu null zum Kennzeichnen derer ohne Nachfolger */
		private static Validator START = null;
		
		private final Map <Validator, List <Validator>> queue = new HashMap<>();
		
		private ValidatorQueue () {}
		
		private void enqueue (Validator validator, Validator previentValidator) {
			List <Validator> validators = queue.get(previentValidator);
			if (validators == null) {
				validators = new ArrayList<>();
				queue.put(previentValidator, validators);
			}
			validators.add(validator);
		}
		private void enqueue (Validator validator) {
			List <Validator> validators = queue.get(START);
			if (validators == null) {
				validators = new ArrayList<>();
				queue.put(START, validators);
			}
			validators.add(validator);
		}
		
		public List <Validator> getSuccessingValidators (Validator precursor) {
			return queue.containsKey(precursor)? 
					Collections.unmodifiableList(queue.get(precursor)): 
					Collections.emptyList();
		}
		public List <Validator> getStartingValidators () {
			return getSuccessingValidators(START);
		}
		
		public static ValidatorQueue build (List <Validator> validatorsToQueue) {
			ValidatorQueue result = new ValidatorQueue();
			for (Validator validator: validatorsToQueue) {
				ValidatorBean metaData = validator.getClass().getAnnotation(ValidatorBean.class);
				
				if (metaData != null && !ValidatorBean.NULL_REP.equals(metaData.previentValidator())) {
					Validator previentValidator = findMatching(validatorsToQueue, metaData.previentValidator());
					if (previentValidator != null) {
						result.enqueue(validator, previentValidator);
						/* continue if there is a precursor, otherwise go to end and enqueue this validator without
						 * a precursor. */
						continue;
					}
				} 
				result.enqueue(validator);
			}
			return result;
		}
		
		private static Validator findMatching (List <Validator> validatorsToSearchIn, 
												Class <? extends Validator> validatorClass) {
			return validatorsToSearchIn.stream()
										.filter(validator -> validatorClass.isAssignableFrom(validator.getClass()))
										.findFirst()
										.orElse(null);
		}
	}
}
