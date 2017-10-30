package de.nak.iaa.housework.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.StudentsClass;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.ValidationException;

@RestController
@RequestMapping("/studentsClass")
public class StudentsClassController {

  private final DomainService<StudentsClass> studentsClassService;

  @Autowired
  public StudentsClassController(final DomainService<StudentsClass> studentsClassService) {
    this.studentsClassService = studentsClassService;
  }

  @GetMapping
  public Collection<StudentsClass> readAll() {
    return studentsClassService.readAll();
  }
  @GetMapping(path="/cohort")
  public Collection<StudentsClass> readAllCohorts() {
    return studentsClassService.readAll();
  }
  @GetMapping(path="/maniple")
  public Collection<StudentsClass> readAllManiples() {
    return studentsClassService.readAll();
  }
  @PostMapping
  public StudentsClass create(@RequestBody final StudentsClass studentsClass) throws ValidationException {
    return studentsClassService.persist(studentsClass);
  }
}
