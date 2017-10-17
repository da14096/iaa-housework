package de.nordakademie.iaa.lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.nordakademie.iaa.lecture.model.Lecture;
import de.nordakademie.iaa.lecture.model.LectureRepository;
import de.nordakademie.iaa.room.model.Room;
import de.nordakademie.iaa.room.model.RoomRepository;

@Service
public class LectureService {

  private final LectureRepository lectureRepository;
  private final RoomRepository roomRepository;

  @Autowired
  public LectureService(final LectureRepository lectureRepository, final RoomRepository roomRepository) {
    this.lectureRepository = lectureRepository;
    this.roomRepository = roomRepository;
  }

  @Transactional(readOnly = true)
  public List<Lecture> findAll() {
    return lectureRepository.findAll();
  }

  @Transactional
  public Lecture create(final Lecture lecture) {
    final Room room = roomRepository.findOne(lecture.getRoom().getId());
    lecture.setRoom(room);
    return lectureRepository.create(lecture);
  }
}
