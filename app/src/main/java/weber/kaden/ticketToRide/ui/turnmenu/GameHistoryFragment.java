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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class GameHistoryFragment extends DialogFragment {
    //widgets
    private Button mActionOk;
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
        View view = inflater.inflate(R.layout.fragment_game_history, container, false);
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActionOk = view.findViewById(R.id.ok_game_history);
        mInflater = inflater;
        mGameHistoryPresenter = new GameHistoryPresenter(this, new ClientFacade());

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mRecyclerView = view.findViewById(R.id.commands);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(new GameHistoryAdapter(mGameHistoryPresenter.getCommands()));


        return view;
    }



    private class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>{

        private List<CommandData> mCommandData;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView commandRow;
            TextView userRow;

            public ViewHolder(View itemView) {
                super(itemView);
                this.commandRow = itemView.findViewById(R.id.command_row);
                this.userRow = itemView.findViewById(R.id.user_row);
            }
        }

        public GameHistoryAdapter(List<CommandData> commandData) {
            mCommandData = new ClientFacade().getGameHistory();
        }

        @Override
        public int getItemCount() {
            return mCommandData.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.game_history_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.commandRow.setText(mCommandData.get(position).getType().toString());
            holder.userRow.setText(mCommandData.get(position).getParams().get(0));
        }
    }
}
