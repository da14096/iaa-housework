package de.nak.iaa.housework.service.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;

/**
 * Validiert sowohl, dass sich die Ereignisse einer Zenturie nicht überschneiden, als auch dass die Pausenzeiten
 * zwischen den einzelnen Ereignissen eingehalten werden.
 * 
 * @author da0015 14096
 */
@ValidatorBean
public class StudentsClassEventsValidator extends TypeOrientedValidator<StudentsClass> {
	
	public StudentsClassEventsValidator() {
		super (StudentsClass.class);
	}
	
	@Override
	public List<Violation> validate(StudentsClass entity) {
		
		List <Event> eventsSortedByStart = entity.getEventsToAttend()
													.stream()
													.sorted((e1, e2) -> e1.getStart().isBefore(e2.getStart())? -1: 1)
													.collect(Collectors.toList());
				
		List <Violation> violations = new ArrayList<>();
		Event previousEvent = null;
		for (Event event: eventsSortedByStart) {			
			if (previousEvent != null) {
				if (event.getStart().isBefore(previousEvent.getEnd())) {
					violations.add(new Violation("Die beiden Veranstaltungen [" + previousEvent + "] und [" + event + "] "
						+ " welche der Zenturie [" + entity + "] zugeordnet sind überschneiden sich. Zenturien können "
								+ "nur an einem Ereignis zur Zeit teilnehmen"));
				} else {
					LocalDateTime maxPrevEnd = event.getStart().minus(entity.getMinimalBreakTime(), ChronoUnit.MINUTES);
					if (maxPrevEnd.isBefore(previousEvent.getEnd())) {
						violations.add(new Violation("Bitte die Pausenzeit der [" + entity + "] beachten. Zwischen den "
								+ " Veranstaltungen [" + previousEvent + "] und [" + event + "] liegt keine Pause von "
								+ entity.getMinimalBreakTime() + " Minuten."));
					}
				}
			}
			previousEvent = event;
		}
		return violations;
	}

}
