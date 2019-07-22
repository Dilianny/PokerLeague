package edu.csc4350.steve1.poker.views.tournament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Tournament;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

import static edu.csc4350.steve1.poker.views.player.DetailsActivity.EXTRA_PLAYER_ID;


public class TournamentDetailsFragment extends Fragment {

    private Tournament tournament;
    private TextView gameNameTV;
    private TextView gameDate;
    private TextView venueName;

    public TournamentDetailsFragment() {
        // Required empty public constructor
    }

    public static TournamentDetailsFragment newInstance(long tournameId) {
        TournamentDetailsFragment fragment = new TournamentDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_PLAYER_ID, tournameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tournament_details, container, false);

        gameNameTV = view.findViewById(R.id.gameName);
        gameDate = view.findViewById(R.id.gameDate);
        venueName = view.findViewById(R.id.venueName);

        // Get the band ID from the intent that started DetailsActivity
        long playerId = 1;
        if (getArguments() != null) {
            playerId = getArguments().getLong(EXTRA_PLAYER_ID);
        }
        updatePlayerDetails(playerId);
        return view;
    }

    public void updatePlayerDetails(long tournament) {
        this.tournament = PokerDatabase.getInstance(getContext()).getTournament(tournament);
        gameNameTV.setText(this.tournament.getGame());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(this.tournament.getDate());
        gameDate.setText(date);
        Venue venue = PokerDatabase.getInstance(getContext()).getVenue(this.tournament.getVenue());
        venueName.setText(venue.getName());
    }

    @Override
    public void onResume() {
        if (tournament != null) {
            updatePlayerDetails(tournament.getId());
        }
        super.onResume();
    }
}
