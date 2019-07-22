package edu.csc4350.steve1.poker.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.model.Venue;

public class VenueViewHolder extends RecyclerView.ViewHolder {
    private TextView venueTextView;

    public VenueViewHolder(View itemView) {
        super(itemView);

        venueTextView = (TextView) itemView;
    }

    public void bindView(Venue venue) {
        venueTextView.setText(venue.getName());
    }
}