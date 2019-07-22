package edu.csc4350.steve1.poker.model;;

import java.util.Date;

public class Tournament{
    private long id;
    private String game;
    private long venue;
    private Date date;
    private Player place_1;
    private Player place_2;
    private Player place_3;

    public Tournament(){
        id = 0;
        game = "NL Hold'em";
        venue = 0;
        date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getVenue() {
        return venue;
    }

    public void setVenue(long venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Player getPlace_1() {
        return place_1;
    }

    public void setPlace_1(Player place_1) {
        this.place_1 = place_1;
    }

    public Player getPlace_2() {
        return place_2;
    }

    public void setPlace_2(Player place_2) {
        this.place_2 = place_2;
    }

    public Player getPlace_3() {
        return place_3;
    }

    public void setPlace_3(Player place_3) {
        this.place_3 = place_3;
    }

    @Override
    public String toString() {
        return date.toString() + " - " + venue;
    }
}
