package de.nak.iaa.housework.model;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 * Diese Klasse wird verwendet um den komplexen Schlüssel der Zenturiennamen abzubilden
 * 
 * @author Nico Kriebel
 */
@Embeddable
public class StudentsClassId implements Serializable {

	private static final long serialVersionUID = -7238116374329603920L;

	private FieldOfStudy fieldOfStudy;
	private int year;
	private char form;

	public StudentsClassId() { }
	
	public StudentsClassId(FieldOfStudy fieldOfStudy, int year, char form) {
		this.fieldOfStudy = fieldOfStudy;
		this.year = year;
		this.form = form;
	}

	public String getFormName() {
		String yearAsString = String.valueOf(year);
		return fieldOfStudy + yearAsString.substring(yearAsString.length() - 2) + form;
	}
	public FieldOfStudy getFieldOfStudy() {
		return fieldOfStudy;
	}
	public int getYear() {
		return year;
	}
	public char getForm() {
		return form;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
		result = prime * result + form;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentsClassId other = (StudentsClassId) obj;
		if (fieldOfStudy != other.fieldOfStudy)
			return false;
		if (form != other.form)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}