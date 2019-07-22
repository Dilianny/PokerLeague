package edu.csc4350.steve1.poker.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.model.Tournament;
import edu.csc4350.steve1.poker.model.Venue;

public class PokerDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "pokerleague.db";

    private static PokerDatabase mPokerDb;
    private static final Object object = new Object();

    private PokerDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static PokerDatabase getInstance(Context context) {
        synchronized (object) {
            if (mPokerDb == null) {
                mPokerDb = new PokerDatabase(context);
            }
        }
        return mPokerDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "";
        // Create players table
        sql = "create table " + PlayerTable.TABLE + " (" +
                PlayerTable.COL_ID + " integer primary key autoincrement, " +
                PlayerTable.COL_FIRST_NAME + ", " +
                PlayerTable.COL_LAST_NAME + ", " +
                PlayerTable.COL_POINT + " )";
        Log.d("PokerDatabase", sql);
        db.execSQL(sql);

        // Create questions table with foreign key that cascade deletes
        sql = "create table " + VenueTable.TABLE + " (" +
                VenueTable.COL_ID + " integer primary key autoincrement, " +
                VenueTable.COL_NAME + ", " +
                VenueTable.COL_ADDRESS + " )";
        Log.d("PokerDatabase", sql);
        db.execSQL(sql);

        sql = "create table " + TournamentTable.TABLE + " (" +
                TournamentTable.COL_ID + " integer primary key autoincrement, " +
                TournamentTable.COL_GAME + ", " +
                TournamentTable.COL_DATE + ", " +
                TournamentTable.COL_VENUE + " integer, " +
                "foreign key(" + TournamentTable.COL_VENUE + ") references " +
                VenueTable.TABLE + "(" + VenueTable.COL_ID + ") on delete cascade) ";
        Log.d("PokerDatabase", sql);
        db.execSQL(sql);
                 /*+
                "foreign key(" + TournamentTable.COL_PLACE_1 + ") references " +
                PlayerTable.TABLE + "(" + PlayerTable.COL_ID + ") on delete cascade, " +
                "foreign key(" + TournamentTable.COL_PLACE_2 + ") references " +
                PlayerTable.TABLE + "(" + PlayerTable.COL_ID + ") on delete cascade, " +
                "foreign key(" + TournamentTable.COL_PLACE_3 + ") references " +
                PlayerTable.TABLE + "(" + PlayerTable.COL_ID + ") on delete cascade) ");*/


        // Add some players
        Player player = new Player(1, "Steve", "Fassnacht",20);
        ContentValues values = new ContentValues();
        values.put(PlayerTable.COL_FIRST_NAME, player.getFirstName());
        values.put(PlayerTable.COL_LAST_NAME, player.getLastName());
        values.put(PlayerTable.COL_POINT,player.getPoints());
        db.insert(PlayerTable.TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PlayerTable.TABLE);
        db.execSQL("drop table if exists " + VenueTable.TABLE);
        db.execSQL("drop table if exists " + TournamentTable.TABLE);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    public Player getPlayer(long playerId) {
        Player player = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + PlayerTable.TABLE +
                " where " + PlayerTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(playerId)});

        if (cursor.moveToFirst()) {
            player = new Player();
            player.setId(cursor.getInt(0));
            player.setFirstName(cursor.getString(1));
            player.setLastName(cursor.getString(2));
            player.setPoints(cursor.getLong(3));
        }
        cursor.close();

        return player;
    }

    public List<Player> getPlayers(PlayerSortOrder order) {
        List<Player> players = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        switch (order) {
            case ALPHABETIC:
                orderBy = PlayerTable.COL_LAST_NAME + " collate nocase";
                break;
            default:
                orderBy = PlayerTable.COL_LAST_NAME + " collate nocase";
                break;
        }

        String sql = "select * from " + PlayerTable.TABLE + " order by " + orderBy;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setId(cursor.getInt(0));
                player.setFirstName(cursor.getString(1));
                player.setLastName(cursor.getString(2));
                player.setPoints(Long.valueOf(cursor.getString(3)));
                players.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return players;
    }

    public boolean addPlayer(Player player) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlayerTable.COL_FIRST_NAME, player.getFirstName());
        values.put(PlayerTable.COL_LAST_NAME, player.getLastName());
        values.put(PlayerTable.COL_POINT,player.getPoints());
        long id = db.insert(PlayerTable.TABLE, null, values);
        return id != -1;
    }

    public void updatePlayer(Player player) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlayerTable.COL_FIRST_NAME, player.getFirstName());
        values.put(PlayerTable.COL_LAST_NAME, player.getLastName());
        values.put(PlayerTable.COL_POINT,player.getPoints());
        db.update(PlayerTable.TABLE, values,
                PlayerTable.COL_ID + " = ?", new String[]{Long.toString(player.getId())});
    }

    public void deletePlayer(long playerId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PlayerTable.TABLE,
                PlayerTable.COL_ID + " = ?", new String[]{Long.toString(playerId)});
    }

    public List<Venue> getVenues() {
        List<Venue> venues = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + VenueTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Venue venue = new Venue();
                venue.setId(cursor.getInt(0));
                venue.setName(cursor.getString(1));
                venue.setAddress(cursor.getString(2));
                venues.add(venue);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return venues;
    }

    public Venue getVenue(long venueId) {
        Venue venue = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + VenueTable.TABLE +
                " where " + VenueTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(venueId)});

        if (cursor.moveToFirst()) {
            venue = new Venue();
            venue.setId(cursor.getInt(0));
            venue.setName(cursor.getString(1));
            venue.setAddress(cursor.getString(2));
        }
        cursor.close();

        return venue;
    }

    public void addVenue(Venue venue) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(VenueTable.COL_ID, venue.getId());
        values.put(VenueTable.COL_NAME, venue.getName());
        values.put(VenueTable.COL_ADDRESS, venue.getAddress());
        long questionId = db.insert(VenueTable.TABLE, null, values);
        venue.setId(questionId);

        // Change update time in subjects table
        //updateSubject(new Subject(question.getSubject()));
    }

    public void updateVenue(Venue venue) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VenueTable.COL_ID, venue.getId());
        values.put(VenueTable.COL_NAME, venue.getName());
        values.put(VenueTable.COL_ADDRESS, venue.getAddress());
        db.update(VenueTable.TABLE, values,
                VenueTable.COL_ID + " = " + venue.getId(), null);
    }

    public void deleteVenue(long venueId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(VenueTable.TABLE,
                VenueTable.COL_ID + " = " + venueId, null);
    }

    public List<Tournament> getTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TournamentTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Tournament tournament = new Tournament();
                tournament.setId(cursor.getInt(0));
                tournament.setGame(cursor.getString(1));
                tournament.setDate(new Date(cursor.getLong(2)));
                tournament.setVenue(cursor.getInt(3));
                tournaments.add(tournament);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return tournaments;
    }

    public Tournament getTournament(long tournamentId) {
        Tournament tournament = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TournamentTable.TABLE +
                " where " + TournamentTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(tournamentId)});

        if (cursor.moveToFirst()) {
            tournament = new Tournament();
            tournament.setId(cursor.getInt(0));
            tournament.setGame(cursor.getString(1));
            tournament.setDate(new Date(cursor.getLong(2)));
            tournament.setVenue(cursor.getInt(3));
        }
        cursor.close();
        return tournament;
    }

    public void addTournament(Tournament tournament) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TournamentTable.COL_GAME, tournament.getGame());
        values.put(TournamentTable.COL_DATE, tournament.getDate().getTime());
        values.put(TournamentTable.COL_VENUE, tournament.getVenue());
        long tournamentId = db.insert(TournamentTable.TABLE, null, values);
        tournament.setId(tournamentId);
        Log.d("PokerDatabase", "Adding tournament " + tournamentId + ".");
    }

    public void updateTournament(Tournament tournament) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TournamentTable.COL_ID, tournament.getId());
        values.put(TournamentTable.COL_GAME, tournament.getGame());
        values.put(TournamentTable.COL_DATE, tournament.getDate().getTime());
        values.put(TournamentTable.COL_VENUE, tournament.getVenue());
        db.update(TournamentTable.TABLE, values,
                VenueTable.COL_ID + " = " + tournament.getId(), null);
    }

    public void deleteTournament(long tournamentId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TournamentTable.TABLE,
                TournamentTable.COL_ID + " = " + tournamentId, null);
    }

    public enum PlayerSortOrder {ALPHABETIC, POINTS}

    private static final class PlayerTable {
        private static final String TABLE = "players";
        private static final String COL_ID = "_id";
        private static final String COL_FIRST_NAME = "firstName";
        private static final String COL_LAST_NAME = "lastName";
        private static final String COL_POINT="points";
    }

    private static final class VenueTable {
        private static final String TABLE = "venues";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_ADDRESS = "address";
    }

    private static final class TournamentTable {
        private static final String TABLE = "tournaments";
        private static final String COL_ID = "_id";
        private static final String COL_GAME = "game";
        private static final String COL_VENUE = "venue";
        private static final String COL_DATE = "date";
        private static final String COL_PLACE_1 = "place_1";
        private static final String COL_PLACE_2 = "place_2";
        private static final String COL_PLACE_3 = "place_3";
    }

}
