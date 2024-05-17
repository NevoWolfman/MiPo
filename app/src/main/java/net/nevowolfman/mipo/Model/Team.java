package net.nevowolfman.mipo.Model;

import java.util.LinkedList;
import java.util.List;

public class Team {
    private String name;
    private List<Member> members;

    public Team(String name, List<Member> members) {
        this.name = name;
        this.members = members;
    }

    public Team() {
        this.name = "";
        this.members = new LinkedList<>();
    }

    public Team(String name) {
        this.name = name;
        this.members = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
