package net.nevowolfman.mipo.Model;

import java.util.LinkedList;
import java.util.List;

//represents a member of a team
public class Member {
    private int id;
    private String Name;

    //the team the member is a part of
    private Team team;

    //the teams that the member is in charge of
    //null if he isn't in charge of any teams
    private Team underlings;

    public Member(int id, String name, Team team) {
        this.id = id;
        Name = name;
        this.team = team;
    }

    public Member(int id, String name, Team team, Team underlings) {
        this.id = id;
        Name = name;
        this.team = team;
        this.underlings = underlings;
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

    public Team getUnderlings() {
        return underlings;
    }

    public void setUnderlings(Team underlings) {
        this.underlings = underlings;
    }
    public boolean hasUnderlings() {
        return underlings != null;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", Name='" + Name + '\'' +
                ", team=" + team.getName();
    }
}