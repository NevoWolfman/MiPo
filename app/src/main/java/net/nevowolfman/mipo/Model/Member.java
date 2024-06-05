package net.nevowolfman.mipo.Model;

import java.util.LinkedList;
import java.util.List;

//represents a member of a team
public class Member {
    private int id;
    private String Name;

    //the teams that the member is in charge of
    //null if he isn't in charge of any teams
    private Team underlings;

    public Member(int id, String name) {
        this.id = id;
        Name = name;
    }

    public Member(int id, String name, Team underlings) {
        this.id = id;
        Name = name;
        this.underlings = underlings;
    }

    public Member(){}

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

    public Team getUnderlings() {
        return underlings;
    }

    public void setUnderlings(Team underlings) {
        this.underlings = underlings;
    }
    public boolean hasUnderlings() {
        return underlings != null;
    }
}