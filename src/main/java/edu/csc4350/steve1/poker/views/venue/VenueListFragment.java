package edu.csc4350.steve1.poker.views.venue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.csc4350.steve1.poker.R;
import edu.csc4350.steve1.poker.adapters.VenueRecyclerAdapters;
import edu.csc4350.steve1.poker.model.Venue;
import edu.csc4350.steve1.poker.providers.PokerDatabase;


public class VenueListFragment extends Fragment {
    // Reference to the activity
    private OnVenueSelectedListener onVenueSelectedListener;


    private PokerDatabase pokerDatabase;
    private VenueRecyclerAdapters bandRecyclerAdapters;

    private FloatingActionButton addVenueButton;
    private RecyclerView recyclerView;

    // onCreateView is omitted
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        addVenueButton = view.findViewById(R.id.addVenueButton);
        addVenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddVenueActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        pokerDatabase = PokerDatabase.getInstance(getContext());

        // Create the buttons using the band names and ids from BandDatabase
        List<Venue> venueList = pokerDatabase.getVenues();
        bandRecyclerAdapters = new VenueRecyclerAdapters(venueList, onVenueSelectedListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(bandRecyclerAdapters);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(bandRecyclerAdapters));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onResume() {
        if (bandRecyclerAdapters != null && pokerDatabase != null) {
            bandRecyclerAdapters.updateVenues(pokerDatabase.getVenues());
        }
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVenueSelectedListener) {
            onVenueSelectedListener = (OnVenueSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnBandSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onVenueSelectedListener = null;
    }

    // For the activity to implement
    public interface OnVenueSelectedListener {
        void onVenueSelected(long venueId);

        void onVenueDeleted(long venueId);
    }

    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private final ColorDrawable background;
        private VenueRecyclerAdapters mAdapter;

        public SwipeToDeleteCallback(VenueRecyclerAdapters adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;
            background = new ColorDrawable(Color.RED);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            if (dX > 0) { // Swiping to the right
                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());

            } else if (dX < 0) { // Swiping to the left
                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }
            background.draw(c);
        }
    }
}