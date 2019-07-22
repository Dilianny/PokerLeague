package edu.csc4350.steve1.poker.views.venue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

public class AddVenueActivity extends AppCompatActivity {

    public static String ARG_VENUE_ID = "venueId";

    private EditText edtName, edtAddress;
    private Button addVenueButton;

    private PokerDatabase venueDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);

        final long venueId = getIntent().getLongExtra(ARG_VENUE_ID, -1);
        venueDatabase = PokerDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);

        addVenueButton = findViewById(R.id.addVenueButton);

        if (venueId != -1){
            Venue venue = venueDatabase.getVenue(venueId);
            edtName.setText(venue.getName());
            edtAddress.setText(venue.getAddress());

            toolbar.setTitle("Update Venue");
            addVenueButton.setText("Update Venue");
        }

        addVenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();


                if (name == null || name.isEmpty()){
                    edtName.setError("Name is empty!");
                    return;
                }

                if (address == null || address.isEmpty()){
                    edtAddress.setError("Address is empty!");
                    return;
                }


                if (venueId != -1){
                    Venue venue = new Venue(venueId, name, address);
                    venueDatabase.updateVenue(venue);
                } else {
                    Venue venue = new Venue(name, address);
                    venueDatabase.addVenue(venue);
                }
                Intent intent = new Intent();
                intent.putExtra(ARG_VENUE_ID, venueId);
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
