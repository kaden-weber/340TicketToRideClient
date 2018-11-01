package weber.kaden.myapplication.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.myapplication.R;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
    private List<DestinationCard> destinationCards;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    GameAdapter(Context context, List<DestinationCard> destinationCards) {
        this.mInflater = LayoutInflater.from(context);
        this.destinationCards = destinationCards;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.individual_card_for_map, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DestinationCard destinationCard =  destinationCards.get(position);
        String destinationText = destinationCard.getStartCity().toString() + "\nTo\n" + destinationCard.getEndCity().toString();
        holder.toggleDest.setTextOn(destinationText);
        holder.toggleDest.setTextOff(destinationText);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return destinationCards.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleDest;
        ViewHolder(View itemView) {
            super(itemView);
            toggleDest = itemView.findViewById(R.id.destination_card_generic);
        }
    }
}
