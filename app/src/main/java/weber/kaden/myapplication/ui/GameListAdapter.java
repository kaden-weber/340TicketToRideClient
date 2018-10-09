package weber.kaden.myapplication.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;
import weber.kaden.myapplication.R;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private List<Game> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    GameListAdapter(Context context, List<Game> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_row, parent, false);
        return new ViewHolder(view);
    }
    public String playersToString(List<Player> players){
        String playerString = "";
        for(Player player : players){
            playerString += player.getID();
        }
        playerString += "  ";
        return playerString;
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String gameInfo = "Game ID: " + mData.get(position).getGameName() + " Players: " +
                playersToString(mData.get(position).getPlayers());
        holder.myTextView.setText(gameInfo);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button mButton;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.gameName);
            mButton = itemView.findViewById(R.id.join_game_button);
            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getID();
    }
    String getGameName(int id) {
        return mData.get(id).getGameName();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}