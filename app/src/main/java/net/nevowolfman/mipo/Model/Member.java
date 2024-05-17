package net.nevowolfman.mipo.Model;

import java.util.LinkedList;
import java.util.List;

public class Member {
    private int id;
    private String Name;
    //the team the member is in
    private Team team;
    //the teams that the member is in charge of
    //empty if he isn't in charge of any teams
    private List<Team> teams;

    public Member(int id, String name, Team team) {
        this.id = id;
        Name = name;
        this.team = team;
        teams = new LinkedList<>();
    }

    public Member(int id, String name, Team team, List<Team> teams) {
        this.id = id;
        Name = name;
        this.team = team;
        this.teams = teams;
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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public boolean hasTeams() {
        return !teams.isEmpty();
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", Name='" + Name + '\'' +
                ", team=" + team.getName();
    }
}