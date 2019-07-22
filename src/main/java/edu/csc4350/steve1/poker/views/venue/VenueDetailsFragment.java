package edu.csc4350.steve1.poker.views.venue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

import static edu.csc4350.steve1.poker.views.player.DetailsActivity.EXTRA_PLAYER_ID;


public class VenueDetailsFragment extends Fragment {

    private Venue venue;
    private TextView nameTextView;
    private TextView descriptionTextView;

    public VenueDetailsFragment() {
        // Required empty public constructor
    }

    public static VenueDetailsFragment newInstance(long playerId) {
        VenueDetailsFragment fragment = new VenueDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_PLAYER_ID, playerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_venue_details, container, false);

        nameTextView = view.findViewById(R.id.venueName);
        descriptionTextView = view.findViewById(R.id.venueAddress);

        // Get the band ID from the intent that started DetailsActivity
        long venueId = 1;
        if (getArguments() != null) {
            venueId = getArguments().getLong(EXTRA_PLAYER_ID);
        }
        updateVenueDetails(venueId);
        return view;
    }

    public void updateVenueDetails(long venueId) {
        venue = PokerDatabase.getInstance(getContext()).getVenue(venueId);
        nameTextView.setText(venue.getName());
        descriptionTextView.setText( venue.getAddress());
    }

    @Override
    public void onResume() {
        if (venue != null) {
            updateVenueDetails(venue.getId());
        }
        super.onResume();
    }
}
