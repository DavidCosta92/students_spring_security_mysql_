package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // @RestController En el tutorial, pide poner esto , pero cual es la diferencia?
            // Como rest, no me muestra los archivos de thymelefat
@RequestMapping("/")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String renderStudentList(Model model) {
        model.addAttribute("studentList", studentService.getStudentList());
        return "students.html";
    }

    @GetMapping("/studentDetails/{studentId}")
    public String renderStudentById(@PathVariable Integer studentId, Model model) {
        model.addAttribute("student", studentService.getStudentById(studentId));
        return "student.html";
    }

    @PostMapping("/")
    public String addStudent(@RequestParam String name, String surname, Integer age, String birthday, Model model) {
        try {
            studentService.addStudent(name, surname, age, birthday);
            model.addAttribute("studentList", studentService.getStudentList());
            model.addAttribute("exito", "EXITO");
            return "students.html";
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("studentList", studentService.getStudentList());
            return "students.html";
        }
    }

    @PostMapping("/studentDetails/{studentId}")
    public String updateStudent(@PathVariable Integer studentId, @RequestParam String name, String surname, Integer age,
            String birthday, Model model) {
        try {
            studentService.updateStudentById(studentId, name, surname, age, birthday);
            model.addAttribute("student", studentService.getStudentById(studentId));
            model.addAttribute("exito", "Actualizado con exito");
            return "student.html";
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("student", studentService.getStudentById(studentId));
            return "student.html";
        }
    }

    @PostMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable Integer studentId, Model model) {
        try {
            studentService.deleteStudentById(studentId);
            return "redirect:/";
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("studentList", studentService.getStudentList());
            return "students.html";
        }
    }

    @PostMapping("/search/")
    public String searchStudent(@RequestParam(required = false) String searchData,
            @RequestParam(required = false) String searchBy,
            Model model) {
        try {
            List<Student> students;
            if (searchBy != null && searchBy.equals("name")) {
                students = studentService.searchStudentsByName(searchData);
            } else if (searchBy != null && searchBy.equals("surname")) {
                students = studentService.searchStudentsBySurname(searchData);
            } else {
                students = studentService.getStudentList();
            }
            model.addAttribute("studentList", students);
            return "students.html";
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("studentList", studentService.getStudentList());
            return "students.html";
        }
    }
}
