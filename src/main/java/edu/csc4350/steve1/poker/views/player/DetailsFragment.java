package edu.csc4350.steve1.poker.views.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.providers.PokerDatabase;

import static edu.csc4350.steve1.poker.views.player.DetailsActivity.EXTRA_PLAYER_ID;


public class DetailsFragment extends Fragment {

    private Player player;
    private TextView nameTextView;
    private TextView descriptionTextView;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(long playerId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_PLAYER_ID, playerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        nameTextView = view.findViewById(R.id.playerName);
        descriptionTextView = view.findViewById(R.id.playerPoint);

        // Get the band ID from the intent that started DetailsActivity
        long playerId = 1;
        if (getArguments() != null) {
            playerId = getArguments().getLong(EXTRA_PLAYER_ID);
        }
        updatePlayerDetails(playerId);
        return view;
    }

    public void updatePlayerDetails(long playerId) {
        player = PokerDatabase.getInstance(getContext()).getPlayer(playerId);

        nameTextView.setText(String.format("%s %s", player.getFirstName(), player.getLastName()));
        descriptionTextView.setText(String.valueOf(player.getPoints()));
    }

    @Override
    public void onResume() {
        if (player != null) {
            updatePlayerDetails(player.getId());
        }
        super.onResume();
    }
}
