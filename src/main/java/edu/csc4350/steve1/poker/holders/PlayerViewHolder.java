package edu.csc4350.steve1.poker.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.csc4350.steve1.poker.model.Player;

public class PlayerViewHolder extends RecyclerView.ViewHolder {
    private TextView playerTextView;

    public PlayerViewHolder(View itemView) {
        super(itemView);

        playerTextView = (TextView) itemView;
    }

    public void bindView(Player player) {

        // Set the text to the player's name
        playerTextView.setText(String.format("%s %s", player.getFirstName(), player.getLastName()));
    }
}