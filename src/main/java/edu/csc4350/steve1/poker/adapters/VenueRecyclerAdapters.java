package edu.csc4350.steve1.poker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.holders.VenueViewHolder;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.views.venue.VenueListFragment;

public class VenueRecyclerAdapters extends RecyclerView.Adapter<VenueViewHolder> {
    private List<Venue> venues;
    private VenueListFragment.OnVenueSelectedListener onVenueSelectedListener;

    public VenueRecyclerAdapters(List<Venue> venueList, VenueListFragment.OnVenueSelectedListener  onVenueSelectedListener) {
        this.venues = venueList;
        this.onVenueSelectedListener = onVenueSelectedListener;
    }

    public void updateVenues(List<Venue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Venue venue = venues.get(position);
        this.onVenueSelectedListener.onVenueDeleted((venue.getId()));
        this.venues.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        final Venue venue = venues.get(position);
        holder.bindView(venue);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVenueSelectedListener != null) {
                    onVenueSelectedListener.onVenueSelected(venue.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }
}
