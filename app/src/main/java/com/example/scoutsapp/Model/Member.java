package com.example.scoutsapp.Model;

import java.util.List;

public class Member {
    int id;
    String Name;
    int grade;
    String role;
    String role_type;

    //the teams that the member is in charge of
    //can be null if he isn't in charge of any teams
    List<Team> team;

    public Member(int id, String name, int grade, String role, String role_type) {
        this.id = id;
        Name = name;
        this.grade = grade;
        this.role = role;
        this.role_type = role_type;
    }

    public Member(int id, String name, int grade, String role, String role_type, List<Team> team) {
        this.id = id;
        Name = name;
        this.grade = grade;
        this.role = role;
        this.role_type = role_type;
        this.team = team;
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

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }
}