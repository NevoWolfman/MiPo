package net.nevowolfman.mipo.Repository;

import net.nevowolfman.mipo.Model.Organization;

import java.util.ArrayList;
import java.util.List;

//represents a user of the app
public class UserModel {
    private String email;
    private String password;
    private List<Organization> orgs;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
        orgs = new ArrayList<>();
    }
    public UserModel() {}

    public UserModel(String email, String password, List<Organization> orgs) {
        this.email = email;
        this.password = password;
        this.orgs = orgs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }
}
