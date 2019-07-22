package edu.csc4350.steve1.poker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.holders.TournamentViewHolder;
import edu.csc4350.steve1.poker.model.Tournament;
import edu.csc4350.steve1.poker.views.tournament.TournamentListFragment;

public class TournamentRecyclerAdapters extends RecyclerView.Adapter<TournamentViewHolder> {
    private List<Tournament> tournaments;
    private TournamentListFragment.OnTournamentSelectedListener onPlayerSelectedListener;

    public TournamentRecyclerAdapters(List<Tournament> tournaments, TournamentListFragment.OnTournamentSelectedListener onPlayerSelectedListener) {
        this.tournaments = tournaments;
        this.onPlayerSelectedListener = onPlayerSelectedListener;
    }

    public void updatePlayers(List<Tournament> players) {
        this.tournaments = players;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Tournament tournament = tournaments.get(position);
        this.onPlayerSelectedListener.onTournamentDeleted(tournament.getId());
        this.tournaments.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        final Tournament tournament = tournaments.get(position);
        holder.bindView(tournament);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlayerSelectedListener != null) {
                    onPlayerSelectedListener.onTournamentSelected(tournament.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }
}
