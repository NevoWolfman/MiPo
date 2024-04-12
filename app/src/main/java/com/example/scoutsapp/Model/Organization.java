package com.example.scoutsapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String Name;
    private Team admins;

    public Organization(String name, Team admins) {
        Name = name;
        this.admins = admins;
    }

    public Organization(String name) {
        Name = name;
        this.admins = new Team("admins");
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Team getAdmins() {
        return admins;
    }

    public void setAdmins(Team admins) {
        this.admins = admins;
    }
}
