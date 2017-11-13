package de.nak.iaa.housework.service.validation;

import java.util.Set;

public interface ValidationService {
	
	<ET> Set <Violation> validate (ET entity);

}
