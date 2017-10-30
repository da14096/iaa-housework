package de.nak.iaa.housework.service.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultValidationService implements ValidationService {

	@Autowired
	private List <Validator> validators;
	
	private final Map <Class, List <Validator>> responsibleValidators = new HashMap<>();
	
	@Override
	public <ET> List<Violation> validate(ET entity) {
		return getResponsibleValidators(entity)
				.stream()
				.map(validator -> validator.validate(entity))
				.collect(() -> new ArrayList<>(), (l1, l2) -> l1.addAll(l2), (l1, l2) -> l1.addAll(l2));
	}
	
	private <ET> List <Validator> getResponsibleValidators (ET entity) {
		Class <?> entityType = entity.getClass();
		List <Validator> validatorList = responsibleValidators.get(entityType);
		if (validatorList == null) {
			validatorList = validators.stream()
										.filter(validator -> validator.isResponsible(entity.getClass()))
										.collect(Collectors.toList());
			responsibleValidators.put(entityType, validatorList);
		} 
		return validatorList;
	}
}
