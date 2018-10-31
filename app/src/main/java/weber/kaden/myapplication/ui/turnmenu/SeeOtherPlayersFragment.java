package weber.kaden.myapplication.ui.turnmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import weber.kaden.common.model.Player;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;

public class SeeOtherPlayersFragment extends DialogFragment {
    //widgets
    private Button mActionCancel, mActionOk;
    private RecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private SeeOtherPlayersPresenter mSeeOtherPlayersPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_see_other_players, container, false);
        mActionCancel = view.findViewById(R.id.see_other_players_cancel);
        mActionOk = view.findViewById(R.id.see_other_players_ok);
        mInflater = inflater;
        mSeeOtherPlayersPresenter = new SeeOtherPlayersPresenter(this, new ClientFacade());

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Make this send data back
                getDialog().dismiss();
            }
        });

        mRecyclerView = view.findViewById(R.id.see_other_players_other_players);
        mRecyclerView.setAdapter(new SeeOtherPlayersAdapter(mSeeOtherPlayersPresenter.getPlayers()));


        return view;
    }

    private class SeeOtherPlayersAdapter extends RecyclerView.Adapter<SeeOtherPlayersAdapter.ViewHolder>{

        private List<Player> mPlayers;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView playerName;
            TextView playerScore;
            TextView playerTrainCards;
            TextView playerDestinationCards;
            TextView playerTrains;

            public ViewHolder(View itemView) {
                super(itemView);
                this.playerName = itemView.findViewById(R.id.see_other_players_row_playerName);
                this.playerScore = itemView.findViewById(R.id.see_other_players_row_score);
                this.playerTrainCards = itemView.findViewById(R.id.see_other_players_row_train_card);
                this.playerDestinationCards = itemView.findViewById(R.id.see_other_players_row_destination_card);
                this.playerTrains = itemView.findViewById(R.id.see_other_players_row_train_pieces);
            }
        }

        public SeeOtherPlayersAdapter(List<Player> players) {
            mPlayers = players;
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.fragment_dialog_see_other_players_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String playerName = "Player: " + mPlayers.get(position).getID();
            String playerScore = "Score: " + mPlayers.get(position).getScore();
            String playerTrainCards = "Train Cards: " + mPlayers.get(position).getNumberOfTrainCards();
            String playerTrainPieces = "Train Pieces: " + mPlayers.get(position).getNumberOfTrains();
            String playerDestinationCards = "Destination Cards: " + mPlayers.get(position).getNumberOfDestinationCards();
            holder.playerName.setText(playerName);
            holder.playerScore.setText(playerScore);
            holder.playerTrains.setText(playerTrainPieces);
            holder.playerTrainCards.setText(playerTrainCards);
            holder.playerDestinationCards.setText(playerDestinationCards);
        }
    }
}
