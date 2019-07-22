package edu.csc4350.steve1.poker.model;

public class Player {
    private long id;
    private String firstName;
    private String lastName;
    private long points;

    public Player(String fn, String ln) {
        firstName = fn;
        lastName = ln;
    }

    public Player(long id, String firstName, String lastName, long points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }

    public Player(String firstName, String lastName, long points) {
        this(firstName, lastName);
        this.points = points;
    }

    public Player() {
        id = 0;
        firstName = "";
        lastName = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

