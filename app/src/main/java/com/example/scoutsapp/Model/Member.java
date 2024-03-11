package com.example.scoutsapp.Model;

import java.util.LinkedList;
import java.util.List;

public class Member {
    private int id;
    private String Name;
    //the team the member is in
    private Team team;
    //the teams that the member is in charge of
    //empty if he isn't in charge of any teams
    private List<Team> goons;

    public Member(int id, String name, Team team) {
        this.id = id;
        Name = name;
        this.team = team;
        goons = new LinkedList<>();
    }

    public Member(int id, String name, Team team, List<Team> goons) {
        this.id = id;
        Name = name;
        this.team = team;
        this.goons = goons;
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

    public List<Team> getGoons() {
        return goons;
    }

    public void setGoons(List<Team> goons) {
        this.goons = goons;
    }

    public boolean hasGoons() {
        return !goons.isEmpty();
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", Name='" + Name + '\'' +
                ", team=" + team.getName();
    }
}