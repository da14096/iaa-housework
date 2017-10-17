package de.nordakademie.iaa.lecture.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nordakademie.iaa.lecture.model.Lecture;
import de.nordakademie.iaa.lecture.service.LectureService;

@RestController
@RequestMapping("/lectures")
public class LectureController {

  private final LectureService lectureService;

  @Autowired
  public LectureController(final LectureService lectureService) {
    this.lectureService = lectureService;
  }

  @GetMapping
  public List<Lecture> findAll() {
    return lectureService.findAll();
  }

  @PostMapping
  public Lecture saveLecture(@RequestBody final Lecture lecture) {
    return lectureService.create(lecture);
  }
}
