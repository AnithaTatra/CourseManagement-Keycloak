package com.authenticate.keycloak.controller;

import com.authenticate.keycloak.model.Course;
import com.authenticate.keycloak.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable("id") long id) throws JsonProcessingException {
        Course course = courseService.getCourse(id);
        return course;
    }

    @PostMapping("/courses")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.addCourse(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCourse.getCode()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/courses/{id}")

    public void deleteStudent(@PathVariable long id) {
        System.out.println("calling delete operation");
        courseService.deleteById(id);
    }



}
