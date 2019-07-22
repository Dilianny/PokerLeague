package edu.csc4350.steve1.poker.views.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

public class AddPlayerActivity extends AppCompatActivity {

    public static String ARG_PLAYER_ID = "playerId";

    private EditText edtFirstName, edtLastName,edtPoints;
    private Button addPlayerButton;

    private PokerDatabase playerDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final long playerId = getIntent().getLongExtra(ARG_PLAYER_ID, -1);
        playerDatabase = PokerDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPoints = findViewById(R.id.edtPoints);

        addPlayerButton = findViewById(R.id.addPlayerButton);

        if (playerId != -1){
            Player player = playerDatabase.getPlayer(playerId);
            edtFirstName.setText(player.getFirstName());
            edtLastName.setText(player.getLastName());
            edtPoints.setText(String.valueOf(player.getPoints()));

            toolbar.setTitle("Update Player");
            addPlayerButton.setText("Update Player");
        }

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String points = edtPoints.getText().toString().trim();


                if (firstName == null || firstName.isEmpty()){
                    edtFirstName.setError("First name is empty!");
                    return;
                }

                if (lastName == null || lastName.isEmpty()){
                    edtLastName.setError("Last name is empty!");
                    return;
                }

                if (points == null || points.isEmpty()){
                    edtLastName.setError("Points is empty!");
                    return;
                }


                if (playerId != -1){
                    Player player = new Player(playerId, firstName, lastName,Long.parseLong(points));
                    playerDatabase.updatePlayer(player);
                } else {
                    Player player = new Player(firstName, lastName,Long.parseLong(points));
                    playerDatabase.addPlayer(player);
                }
                Intent intent = new Intent();
                intent.putExtra(ARG_PLAYER_ID, playerId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
