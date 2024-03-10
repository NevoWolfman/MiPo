package com.example.scoutsapp.Model;

import java.util.List;

public class Member {
    private int id;
    private String Name;
    //the team the member is in
    private Team team;
    //the teams that the member is in charge of
    //can be null if he isn't in charge of any teams
    private List<Team> subordinates; // goooooons maybe??????

    public Member(int id, String name, Team team) {
        this.id = id;
        Name = name;
        this.team = team;
    }

    public Member(int id, String name, Team team, List<Team> subordinates) {
        this.id = id;
        Name = name;
        this.team = team;
        this.subordinates = subordinates;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Team> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Team> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", Name='" + Name + '\'' +
                ", team=" + team.getName();
    }
}