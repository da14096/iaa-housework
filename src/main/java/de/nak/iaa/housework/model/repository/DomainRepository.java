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

	/** Notation um über auf ein property einer Entität zuzugreifen */
	static final String PROPERTY_NAVIGATOR = ".";
	
	
	/**
	 * Liefert alle persistenten Objekte zu einem Typ.
	 * 
	 * @param targetType der gewünschte Typ
	 * @return Alle persistenten Objekte vom targetType
	 */
	<TYPE> List <TYPE> readAll (Class <TYPE> targetType);
	
	/**
	 * Liefert alle persistenten Objekte zu einem Typ.
	 * 
	 * @param targetType der gewünschte Typ
	 * @param propertyFilters optional definierbare Filter-Kriterien
	 * @return Alle persistenten Objekte vom targetType
	 */
	<TYPE> List <TYPE> readAll (Class <TYPE> targetType, PropertyFilterChain filter);
	
	
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
	 * Aktualisiert ein Objekt im Persistenzkontext. 
	 * 
	 * @param item das Objekt
	 * @return das Objekt
	 */
	<TYPE> TYPE update (TYPE item);
		
	
	/**
	 * Persistiert eine Objekt. Hier erfolgt auch die ID-Vergabe sofern die ID datenbankseitig generiert wird, 
	 * sodass das zurückgegebene Objekt mit einer ID versehen ist. 
	 * 
	 * @param item das Objekt
	 * @return das Objekt
	 */
	<TYPE> TYPE create (TYPE item);
	
	
	/**
	 * Löscht ein Objekt aus dem Persistenzkontext.
	 * 
	 * @param item das Objekt
	 */
	<TYPE> void delete (TYPE item);	
}
