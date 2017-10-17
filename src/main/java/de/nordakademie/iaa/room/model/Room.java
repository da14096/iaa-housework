package de.nordakademie.iaa.room.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROOM")
public class Room {

  private long id;
  private String building;
  private String number;

  public Room() {
  }

  public Room(final long id, final String building, final String number) {
    this.id = id;
    this.building = building;
    this.number = number;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  @Basic
  public String getBuilding() {
    return building;
  }

  public void setBuilding(final String building) {
    this.building = building;
  }

  @Basic
  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "Room{" + "id=" + id + ", building='" + building + '\'' + ", number='" + number + '\'' + '}';
  }
}
