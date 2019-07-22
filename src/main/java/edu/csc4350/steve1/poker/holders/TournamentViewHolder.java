package edu.csc4350.steve1.poker.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.model.Tournament;

public class TournamentViewHolder extends RecyclerView.ViewHolder {
    private TextView playerTextView;

    public TournamentViewHolder(View itemView) {
        super(itemView);

        playerTextView = (TextView) itemView;
    }

    public void bindView(Tournament tournament) {

        // Set the text to the tournament's name
        playerTextView.setText(tournament.getGame());
    }
}