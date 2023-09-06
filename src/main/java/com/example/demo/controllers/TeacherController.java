package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Teacher;
import com.example.demo.services.TeacherService;

// @Controller

@RestController
@RequestMapping("/teachers/")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public void createTeacher(@RequestBody Teacher teacher) {
        teacherService.createTeacher(teacher);
    }

    @GetMapping
    public String getAllTeachers() {
        return "soy un contenido protegido???";
    }

}
