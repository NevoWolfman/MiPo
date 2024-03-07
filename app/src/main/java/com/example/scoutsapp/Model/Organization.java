package com.example.scoutsapp.Model;

public class Organization {
    String Name;
    MemberTree members;

    public Organization(String name, MemberTree members) {
        Name = name;
        this.members = members;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public MemberTree getMembers() {
        return members;
    }

    public void setMembers(MemberTree members) {
        this.members = members;
    }

    class MemberTree{
        Member[] members;
        MemberTree[] next_levels;

        public MemberTree(Member[] members, MemberTree[] next_levels) {
            this.members = members;
            this.next_levels = next_levels;
        }
        public MemberTree() {}

        public Member[] getMembers() {
            return members;
        }

        public void setMembers(Member[] members) {
            this.members = members;
        }

        public MemberTree[] getNext_level() {
            return next_levels;
        }

        public void setNext_level(MemberTree[] next_levels) {
            this.next_levels = next_levels;
        }
    }
}
