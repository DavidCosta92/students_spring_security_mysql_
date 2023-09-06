package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;

@Service

public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    // @Transactional
    public void createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

}
