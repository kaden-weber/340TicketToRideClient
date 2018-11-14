package weber.kaden.ticketToRide.ui.gameView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;

import static android.graphics.Color.WHITE;

public class TrainCardAdapter extends RecyclerView.Adapter<TrainCardAdapter.ViewHolder>{
    private HashMap<TrainCardType, Integer> trainCards;
    private LayoutInflater mInflater;
    private TrainCardType[] cardTypes = TrainCardType.values();

    // data is passed into the constructor
    TrainCardAdapter(Context context, HashMap<TrainCardType, Integer> trainCards) {
        this.mInflater = LayoutInflater.from(context);
        this.trainCards = trainCards;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.individual_card_for_map, parent, false);
        return new ViewHolder(view);
    }
    static String convert(String str){
        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {
                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {
                    // Convert into Upper-case
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrainCardType type = cardTypes[position];
        int numOfCards = trainCards.get(cardTypes[position]);
        TrainCard trainCard =  new TrainCard(type);
        String train = trainCard.getType().toString();
        try {
            String id = train.toLowerCase();
            String newId = convert(id);
            train += " : " + numOfCards;
            int color = mInflater.getContext().getResources().getColor(mInflater.getContext().getResources().
                    getIdentifier(newId, "color", mInflater.getContext().getPackageName()));
            if (newId.equals("Hopper")){
                holder.toggleDest.setTextColor(WHITE);
            }
            holder.toggleDest.setBackgroundColor(color);
        } catch (Exception e){
            System.out.println(train);
        }
        holder.toggleDest.setTextOn(train);
        holder.toggleDest.setTextOff(train);
        holder.toggleDest.setText(train);
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
