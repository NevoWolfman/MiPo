package com.example.scoutsapp.Model;

public class Member {
    int id;
    String Name;
    int grade;
    String role;
    String role_type;

    public Member(int id, String name, int grade, String role, String role_type) {
        this.id = id;
        Name = name;
        this.grade = grade;
        this.role = role;
        this.role_type = role_type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getRole() {
        return role;
    }

    public String getRole_type() { return role_type; }

    public void setRole_type(String role_type) { this.role_type = role_type; }

    public void setRole(String role) {
        this.role = role;
    }


    public boolean isThisRole(String role)
    {
        return this.role.equals(role);
    }

    public boolean isThisID(int id)
    {
        return (this.id == id);
    }
}