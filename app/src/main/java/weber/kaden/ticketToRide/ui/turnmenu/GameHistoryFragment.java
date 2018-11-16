package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class GameHistoryFragment extends DialogFragment {
    //widgets
    private Button mActionCancel, mActionOk;
    private RecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private GameHistoryPresenter mGameHistoryPresenter;

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_see_other_players, container, false);
        mActionCancel = view.findViewById(R.id.see_other_players_cancel);
        mActionOk = view.findViewById(R.id.see_other_players_ok);
        mInflater = inflater;
        ((TextView) view.findViewById(R.id.see_other_players_header)).setText("Game History");
        mGameHistoryPresenter = new GameHistoryPresenter(this, new ClientFacade());
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(new GameHistoryAdapter(mGameHistoryPresenter.getCommands()));


        return view;
    }



    private class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>{

        private List<CommandData> mCommandData;

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

        public GameHistoryAdapter(List<CommandData> commandData) {
            mCommandData = commandData;
        }

        @Override
        public int getItemCount() {
            return mCommandData.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.fragment_dialog_see_other_players_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String playerName = "Player: " + mCommandData.get(position).getParams().get(1);
            holder.playerName.setText(playerName);
            String playerScore = "Score: " + mCommandData.get(position).getType().toString();
            holder.playerScore.setText(playerScore);
        }
    }
}
