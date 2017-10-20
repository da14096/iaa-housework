package de.nak.iaa.housework.model.repository;

import java.util.List;

import javax.persistence.Id;

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
	<TYPE> List <TYPE> readAll (Class <TYPE> targetType);
	
	
	/**
	 * Liefert das Objekt welches über die {@link id} identifiziert wird.
	 * Der Typ der hier übergebenen id muss dabei passend zu dem {@link Id} Feld (dem Primary-Key) des
	 * entities sein.
	 * 
	 * @param targetType der erwartete Typ
	 * @param id die Identifikation des Objekts
	 * @return das Objekt, zu der gegebenen id oder null wenn kein solches Objekt vorhanden ist.
	 */
	<TYPE> TYPE find (Class <TYPE> targetType, Object id);
	
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
