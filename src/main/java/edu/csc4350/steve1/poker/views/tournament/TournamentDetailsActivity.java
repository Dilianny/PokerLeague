package edu.csc4350.steve1.poker.views.tournament;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.views.player.AddPlayerActivity;
import edu.csc4350.steve1.poker.views.player.DetailsFragment;

public class TournamentDetailsActivity extends AppCompatActivity {
    public static String EXTRA_TOURNAMENT_ID = "tournament";
    public static String ARG_TOURNAMENT_ID = "tournamentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournamnet_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            long tournamentId = getIntent().getLongExtra(EXTRA_TOURNAMENT_ID, 1L);
            fragment = TournamentDetailsFragment.newInstance(tournamentId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.editPlayer:
                long playerId = getIntent().getLongExtra(EXTRA_TOURNAMENT_ID, 1L);

                Intent intent = new Intent(this, AddTournamentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(ARG_TOURNAMENT_ID, playerId);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
