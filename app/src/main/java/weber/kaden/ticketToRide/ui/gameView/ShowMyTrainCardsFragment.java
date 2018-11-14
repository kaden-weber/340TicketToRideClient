package weber.kaden.ticketToRide.ui.gameView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;

public class ShowMyTrainCardsFragment extends DialogFragment {
    private Button mActionOk;
    TrainCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_show_train_cards, container, false);
        mActionOk = view.findViewById(R.id.showTrainCardsOk);

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        final HashMap<TrainCardType, Integer> trainCards = (HashMap<TrainCardType, Integer>) getArguments().getSerializable("cards");
        RecyclerView recyclerView = view.findViewById(R.id.showTrainCardsRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new TrainCardAdapter(getContext(), trainCards);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
