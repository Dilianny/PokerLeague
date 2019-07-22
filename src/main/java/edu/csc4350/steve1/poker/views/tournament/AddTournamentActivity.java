package edu.csc4350.steve1.poker.views.tournament;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.model.Tournament;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

import static edu.csc4350.steve1.poker.views.tournament.TournamentDetailsActivity.ARG_TOURNAMENT_ID;

public class AddTournamentActivity extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private EditText edtGameName, edtGameDate;
    private Spinner venueSpinner;
    private Button addPlayerButton;
    private Toolbar toolbar;
    private PokerDatabase playerDatabase;

    private ArrayAdapter<Venue> venueItemsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_add);

        final long tournamentId = getIntent().getLongExtra(ARG_TOURNAMENT_ID, -1);
        playerDatabase = PokerDatabase.getInstance(this);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtGameName = findViewById(R.id.edtGameName);
        edtGameDate = findViewById(R.id.edtGameDate);
        venueSpinner = findViewById(R.id.venueSpinner);
        addPlayerButton = findViewById(R.id.addPlayerButton);

        edtGameDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeDatePicker().show();
            }
        });

        List<Venue> venues = PokerDatabase.getInstance(this).getVenues();
        venueItemsAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, venues); //selected item will look like a spinner set from XML
        venueItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        venueSpinner.setAdapter(venueItemsAdapter);

        if (tournamentId != -1) {
            Tournament tournament = playerDatabase.getTournament(tournamentId);
            edtGameName.setText(tournament.getGame());
            edtGameDate.setText(simpleDateFormat.format(tournament.getDate()));

            toolbar.setTitle("Update Tournament");
            addPlayerButton.setText("Update Tournament");
        }

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName = edtGameName.getText().toString().trim();
                String gameDate = edtGameDate.getText().toString().trim();

                Venue venue = (Venue) venueSpinner.getSelectedItem();

                if (gameName == null || gameName.isEmpty()) {
                    edtGameName.setError("Game name is empty!");
                    return;
                }

                if (venue == null){
                    Toast.makeText(AddTournamentActivity.this, "Please select a venue from dropdown", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (gameDate == null || gameDate.isEmpty()){
                    edtGameDate.setError("Game date is empty!");
                    return;
                }

                Date date = null;
                try {
                    date = simpleDateFormat.parse(gameDate);
                } catch (ParseException e) {
                    date = Calendar.getInstance().getTime();
                }

                if (tournamentId != -1) {
                    Tournament tournament = new Tournament();
                    tournament.setId(tournamentId);
                    tournament.setGame(gameName);
                    tournament.setDate(date);
                    tournament.setVenue(venue.getId());
                    playerDatabase.updateTournament(tournament);
                } else {
                    Tournament tournament = new Tournament();
                    tournament.setGame(gameName);
                    tournament.setVenue(venue.getId());
                    tournament.setDate(date);
                    playerDatabase.addTournament(tournament);
                }
                Intent intent = new Intent();
                intent.putExtra(ARG_TOURNAMENT_ID, tournamentId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private DatePickerDialog initializeDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
               new ContextThemeWrapper(this, R.style.AppTheme), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtGameDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
