package com.example.demo.repositories;

import com.example.demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository <Student, Integer> {
    @Query( "SELECT s FROM Student s WHERE s.name LIKE %:name%" )
    public List<Student> searchByName(String name);

    @Query("SELECT s FROM Student s WHERE s.surname LIKE %:surname%")
    public List<Student> searchBySurname(String surname);
}