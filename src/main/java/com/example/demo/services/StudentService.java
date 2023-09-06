package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentService() {
    }
    @Transactional
    public void addStudent(String name, String surname , Integer age , String birthday) throws Exception {
        if(name.length()==0 || surname.length()==0 || birthday.length()==0){
            throw new Exception("Los campos no pueden estar vacios!");
        }
        if(age<0 && age<120){
            throw new Exception("Edad debe estar entre los 0 y 120 Años");
        }
        String[] date = birthday.split("-");
        LocalDate birthday_date = LocalDate.of(Integer.parseInt(date[0]) , Integer.parseInt(date[1]) , Integer.parseInt(date[2]));

        Student newStudent = new Student(name, surname, age, birthday_date);
        studentRepository.save(newStudent);
    }
    @Transactional
    public List<Student> getStudentList() {
        List<Student> students = this.studentRepository.findAll();
        return students;
    }

    @Transactional
    public Student getStudentById(Integer studentId) {
        Student st = studentRepository.getReferenceById(studentId);
        return st;
    }
    @Transactional
    public Student updateStudentById(Integer studentId, String name, String surname , Integer age , String birthday)  throws Exception {
        if(name.length()==0 || surname.length()==0 || birthday.length()==0){
            throw new Exception("Los campos no pueden estar vacios!");
        }
        if(age<0 && age<120){
            throw new Exception("Edad debe estar entre los 0 y 120 Años");
        }
        String[] date = birthday.split("-");
        LocalDate birthday_date = LocalDate.of(Integer.parseInt(date[0]) , Integer.parseInt(date[1]) , Integer.parseInt(date[2]));

        Student st = studentRepository.getReferenceById(studentId);
        st.setName(name);
        st.setSurname(surname);
        st.setAge(age);
        st.setBirthday(birthday_date);
        studentRepository.save(st);
        return st;

    }
    @Transactional
    public void deleteStudentById(Integer studentId) {
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = this.studentRepository.searchByName(name);
        return students;
    }
    @Transactional
    public List<Student> searchStudentsBySurname(String surname) {
        List<Student> students = this.studentRepository.searchBySurname(surname);
        return students;
    }
}
