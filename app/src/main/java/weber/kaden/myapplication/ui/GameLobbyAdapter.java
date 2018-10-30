package weber.kaden.myapplication.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.model.Player;
import weber.kaden.myapplication.R;

public class GameLobbyAdapter extends RecyclerView.Adapter<GameLobbyAdapter.ViewHolder> {
    private List<Player> mData;
    private LayoutInflater mInflater;
    private GameListAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    GameLobbyAdapter(Context context, List<Player> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String player =  (mData.get(position).getID());
        holder.myTextView.setText(player);
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
            myTextView = itemView.findViewById(R.id.playerName);
            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    Player getItem(int id) {
        return mData.get(id);
    }
    String getGameName(int id) {
        return mData.get(id).getID();
    }

}
