package edu.csc4350.steve1.poker.views.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import edu.csc4350.steve1.poker.R;

import static edu.csc4350.steve1.poker.views.player.AddPlayerActivity.ARG_PLAYER_ID;

public class DetailsActivity extends AppCompatActivity {
    public static String EXTRA_PLAYER_ID = "playerId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            long playerId = getIntent().getLongExtra(EXTRA_PLAYER_ID, 1L);
            fragment = DetailsFragment.newInstance(playerId);
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
                long playerId = getIntent().getLongExtra(EXTRA_PLAYER_ID, 1L);

                Intent intent = new Intent(this, AddPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(ARG_PLAYER_ID, playerId);
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
