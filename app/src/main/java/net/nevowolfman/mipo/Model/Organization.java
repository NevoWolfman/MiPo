package net.nevowolfman.mipo.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

//represents an Organization
public class Organization {
    private String name;
    private Team admins;
    private List<EventDate> eventDates;

    public Organization(String name) {
        this.name = name;
        this.admins = new Team("admins");
        eventDates = new ArrayList<>();
    }

    public Organization(String name, EventDate event1) {
        this.name = name;
        this.admins = new Team("admins");
        eventDates = new ArrayList<>();
        eventDates.add(0, event1);
    }

    public Organization() {
        name = "";
        this.admins = new Team();
        eventDates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getAdmins() {
        return admins;
    }

    public void setAdmins(Team admins) {
        this.admins = admins;
    }

    public List<EventDate> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<EventDate> eventDates) {
        this.eventDates = eventDates;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
