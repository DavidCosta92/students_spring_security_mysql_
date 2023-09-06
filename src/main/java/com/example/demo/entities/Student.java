package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    private String fullName;
    private Integer age;
    private LocalDate birthday;

    public Student() {
    }

    public Student(String name, String surname, Integer age, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.birthday = birthday;
        this.fullName = name + "_" +surname + "_" +age.toString();
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return  "ID: " + id +
                "Nombre: " + name +
                ", Apellido: " + surname +
                ", Edad: " + age +
                ", Fecha de nacimiento: " + birthday +
                ", Full name: " + fullName;
    }
}
