package edu.csc4350.steve1.poker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.holders.PlayerViewHolder;
import edu.csc4350.steve1.poker.model.Player;
import edu.csc4350.steve1.poker.views.player.PlayerListFragment;

public class PlayerRecyclerAdapters extends RecyclerView.Adapter<PlayerViewHolder> {
    private List<Player> players;
    private PlayerListFragment.OnPlayerSelectedListener onPlayerSelectedListener;

    public PlayerRecyclerAdapters(List<Player> players, PlayerListFragment.OnPlayerSelectedListener onPlayerSelectedListener) {
        this.players = players;
        this.onPlayerSelectedListener = onPlayerSelectedListener;
    }

    public void updatePlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Player player = players.get(position);
        this.onPlayerSelectedListener.onPlayerDeleted(player.getId());
        this.players.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        final Player player = players.get(position);
        holder.bindView(player);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlayerSelectedListener != null) {
                    onPlayerSelectedListener.onPlayerSelected(player.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
