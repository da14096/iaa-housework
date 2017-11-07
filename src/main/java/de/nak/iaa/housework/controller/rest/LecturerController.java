package de.nak.iaa.housework.controller.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nak.iaa.housework.model.Lecturer;
import de.nak.iaa.housework.service.DomainService;
import de.nak.iaa.housework.service.ValidationException;

@RestController
@RequestMapping("/lecturer")
public class LecturerController {

  private final DomainService<Lecturer> lecturerService;

  @Autowired
  public LecturerController(final DomainService<Lecturer> lecturerService) {
    this.lecturerService = lecturerService;
  }

  @GetMapping
  public Collection<Lecturer> readAll() {
    return lecturerService.readAll();
  }
  @PostMapping (path="/create")
  public Lecturer create(@RequestBody final Lecturer lecturer) throws ValidationException {
    return lecturerService.persist(lecturer);
  }
}
