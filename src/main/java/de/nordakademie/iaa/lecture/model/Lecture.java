package de.nordakademie.iaa.lecture.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.nordakademie.iaa.room.model.Room;

@Entity
@Table(name = "LECTURE")
public class Lecture {

  private long id;
  private String title;
  private Room room;

  public Lecture() {
  }

  public Lecture(final long id, final String title, final Room room) {
    this.id = id;
    this.title = title;
    this.room = room;
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
  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  @ManyToOne(optional = false)
  public Room getRoom() {
    return room;
  }

  public void setRoom(final Room room) {
    this.room = room;
  }
}
