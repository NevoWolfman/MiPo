package net.nevowolfman.mipo.Model;

import java.util.LinkedList;
import java.util.List;

//represents a member of a team
public class Member {
    private String Name;

    //the teams that the member is in charge of
    //null if he isn't in charge of any teams
    private Team underlings;
    private boolean came;

    public Member(String name) {
        Name = name;
        came = false;
    }

    public Member(String name, Team underlings) {
        Name = name;
        this.underlings = underlings;
        came = false;
    }

    public Member(){}

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

    public boolean isCame() {
        return came;
    }

    public void setCame(boolean came) {
        this.came = came;
    }
}