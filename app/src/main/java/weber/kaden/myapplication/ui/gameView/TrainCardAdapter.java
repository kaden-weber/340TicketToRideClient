package weber.kaden.myapplication.ui.gameView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ToggleButton;

import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.R;

import static android.graphics.Color.WHITE;

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
        TrainCard trainCard =  trainCards.get(position);
        String train = trainCard.getType().toString();
        try {
            String id = train.toLowerCase();
            String newId = convert(id);
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
