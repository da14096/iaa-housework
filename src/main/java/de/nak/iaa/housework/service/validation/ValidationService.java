package de.nak.iaa.housework.service.validation;

import java.util.List;

public interface ValidationService {
	
	<ET> List <Violation> validate (ET entity);

}
