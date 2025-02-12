package io.github.slepimis120.employee_manager.model;

public class Employee {
    private String name;
    private String surname;
    private String birthYear;
    private String email;
    private String gender;

    public Employee(String name, String surname, String birthYear, String email, String gender) {
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.email = email;
        this.gender = gender;
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

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
