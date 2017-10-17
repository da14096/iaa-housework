package de.nordakademie.iaa.lecture.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class LectureRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Lecture create(final Lecture lecture) {
    entityManager.persist(lecture);
    return lecture;
  }

  public List<Lecture> findAll() {
    return entityManager.createQuery("SELECT l FROM Lecture l", Lecture.class).getResultList();
  }
}
