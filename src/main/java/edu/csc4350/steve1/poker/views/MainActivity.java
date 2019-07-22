package edu.csc4350.steve1.poker.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.providers.PokerDatabase;
import edu.csc4350.steve1.poker.views.player.DetailsActivity;
import edu.csc4350.steve1.poker.views.player.PlayerListFragment;
import edu.csc4350.steve1.poker.views.tournament.TournamentDetailsActivity;
import edu.csc4350.steve1.poker.views.tournament.TournamentListFragment;
import edu.csc4350.steve1.poker.views.venue.VenueDetailsActivity;
import edu.csc4350.steve1.poker.views.venue.VenueListFragment;

import static edu.csc4350.steve1.poker.views.player.DetailsActivity.EXTRA_PLAYER_ID;
import static edu.csc4350.steve1.poker.views.tournament.TournamentDetailsActivity.EXTRA_TOURNAMENT_ID;
import static edu.csc4350.steve1.poker.views.venue.VenueDetailsActivity.EXTRA_VENUE_ID;

public class MainActivity extends AppCompatActivity implements PlayerListFragment.OnPlayerSelectedListener , TournamentListFragment.OnTournamentSelectedListener, VenueListFragment.OnVenueSelectedListener {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int selectedId = menuItem.getItemId();
            switch (selectedId) {
                case R.id.navigation_player:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.list_fragment_container, new PlayerListFragment())
                            .commitAllowingStateLoss();
                    break;
                case R.id.navigation_tournament:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.list_fragment_container, new TournamentListFragment())
                            .commitAllowingStateLoss();
                    break;
                case R.id.navigation_venue:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_fragment_container, new VenueListFragment())
                        .commitAllowingStateLoss();
                    break;
                default:
                    // do nothing
            }

            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_player);
        }
    }

    @Override
    public void onPlayerSelected(long playerId) {
        // Send the band ID of the clicked button to DetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_PLAYER_ID, playerId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPlayerDeleted(long playerId) {
        PokerDatabase.getInstance(this).deletePlayer(playerId);
    }

    @Override
    public void onTournamentSelected(long tournamentId) {
        // Send the band ID of the clicked button to DetailsActivity
        Intent intent = new Intent(this, TournamentDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_TOURNAMENT_ID, tournamentId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onTournamentDeleted(long tournamentId) {
        PokerDatabase.getInstance(this).deleteTournament(tournamentId);
    }

    @Override
    public void onVenueSelected(long venueId) {
        // Send the band ID of the clicked button to VenueDetailsActivity
        Intent intent = new Intent(this, VenueDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_VENUE_ID, venueId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onVenueDeleted(long venueId) {
        PokerDatabase.getInstance(this).deleteVenue(venueId);
    }
}
