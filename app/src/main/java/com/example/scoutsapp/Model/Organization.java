package com.example.scoutsapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    String Name;
    String Password;
    Team admins;

    public Organization(String name, String password, Team admins) {
        Name = name;
        Password = password;
        this.admins = admins;
    }

    public Organization(String name, String password) {
        Name = name;
        Password = password;
        this.admins = new Team();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Team getAdmins() {
        return admins;
    }

    public void setAdmins(Team admins) {
        this.admins = admins;
    }
}
