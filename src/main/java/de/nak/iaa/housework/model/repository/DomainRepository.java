package de.nak.iaa.housework.model.repository;

import java.util.Collection;

/**
 * Dies ist die Schnittstelle für die Serviceschicht. Hier liegen die Methoden um 
 * Objekete aus dem Persistenzkontext zu lesen/ hineinzuschreiben. 
 * 
 * @author Nico Kriebel
 */
public interface DomainRepository {

	/**
	 * Liefert alle persistenten Objekte zu einem Typ.
	 * 
	 * @param targetType der gewünschte Type
	 * @return Alle persistenten Objekte vom targetType
	 */
	<TYPE> Collection <TYPE> readAll (Class <TYPE> targetType);
	
	/**
	 * Aktualisiert ein Objekt im Persistenzkontext. Ist das Objekt noch nicht persistent
	 * wird es persitiert. Hier erfolgt auch die ID-Vergabe, sodass das zurückgegebene Objekt mit einer ID 
	 * versehen ist. 
	 * 
	 * @param item das Objekt
	 * @return das Objekt
	 */
	<TYPE> TYPE update (TYPE item);
	
	/**
	 * Löscht ein Objekt aus dem Persistenzkontext.
	 * 
	 * @param item das Objekt
	 */
	<TYPE> void delete (TYPE item);
	
	
}
