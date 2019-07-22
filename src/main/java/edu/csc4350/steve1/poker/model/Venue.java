package edu.csc4350.steve1.poker.model;

public class Venue{
    private long id;
    private String name;
    private String address;

    public Venue(String n, String a){
        name = n;
        address = a;
    }

    public Venue(long i, String n, String a){
        this(n,a);
        id = i;
    }

    public Venue(){
        id = 0;
        name = "";
        address = "";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString(){
        return name;
    }
}
