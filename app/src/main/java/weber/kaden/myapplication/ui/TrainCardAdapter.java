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
import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.R;

public class TrainCardAdapter extends RecyclerView.Adapter<TrainCardAdapter.ViewHolder>{
    private  List<TrainCard> trainCards;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    TrainCardAdapter(Context context, List<TrainCard> trainCards) {
        this.mInflater = LayoutInflater.from(context);
        this.trainCards = trainCards;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.individual_card_for_map, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrainCard trainCard =  trainCards.get(position);
        String destinationText = trainCard.getType().toString();
        holder.toggleDest.setTextOn(destinationText);
        holder.toggleDest.setTextOff(destinationText);
        holder.toggleDest.setText(destinationText);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return trainCards.size();
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
