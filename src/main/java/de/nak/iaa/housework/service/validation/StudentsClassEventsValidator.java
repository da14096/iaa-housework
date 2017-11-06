package de.nak.iaa.housework.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;

@ValidatorBean
public class StudentsClassEventsValidator extends TypeOrientedValidator<StudentsClass> {
	
	public StudentsClassEventsValidator() {
		super (StudentsClass.class);
	}
	
	@Override
	public List<Violation> validate(StudentsClass entity) {
		
		List <Event> eventsSortedByStart = entity.getEvents()
													.stream()
													.sorted((e1, e2) -> e1.getStart().isBefore(e2.getStart())? -1: 1)
													.collect(Collectors.toList());
		
		List <Violation> violations = new ArrayList<>();
		Event previousEvent = null;
		for (Event event: eventsSortedByStart) {			
			if (previousEvent != null && event.getStart().isBefore(previousEvent.getEnd())) {
				violations.add(new Violation("Die beiden Ereignisse [" + previousEvent + "] und [" + event + "] "
						+ " welche der Zenturie [" + entity + "] zugeordnet sind überschneiden sich. Zenturien können "
								+ "nur an einem Ereignis zur Zeit teilnehmen"));
			}
			previousEvent = event;
		}
		return violations;
	}

}
