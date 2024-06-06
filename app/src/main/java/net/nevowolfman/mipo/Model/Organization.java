package net.nevowolfman.mipo.Model;

import java.util.ArrayList;
import java.util.List;

//represents an Organization
public class Organization {
    private String Name;
    private Team admins;
    private List<EventDate> eventDates;

    public Organization(String name) {
        Name = name;
        this.admins = new Team("admins");
        eventDates = new ArrayList<>();
    }

    public Organization(String name, EventDate event1) {
        Name = name;
        this.admins = new Team("admins");
        eventDates = new ArrayList<>();
        eventDates.add(0, event1);
    }

    public Organization() {
        Name = "";
        this.admins = new Team();
        eventDates = new ArrayList<>();
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

    public List<EventDate> getEventDates() {
        return eventDates;
    }

    public void setEventDates(List<EventDate> eventDates) {
        this.eventDates = eventDates;
    }
}
