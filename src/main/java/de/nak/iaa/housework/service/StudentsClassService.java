package de.nak.iaa.housework.service;

import de.nak.iaa.housework.model.Event;
import de.nak.iaa.housework.model.StudentsClass;

public interface StudentsClassService extends DomainService<StudentsClass> {

	void addEvent (StudentsClass clazz, Event event);
}
