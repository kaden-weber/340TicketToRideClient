package weber.kaden.ticketToRide.ui.gameView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.ticketToRide.R;

public class ShowMyDestCardsFragment extends DialogFragment {
    private ShowMyDestCardsFragment instance = this;
    private Button mActionOk;
    GameAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_show_dest_cards, container, false);
        mActionOk = view.findViewById(R.id.showDestCardsOk);

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        final List<DestinationCard> destinationCards = (List<DestinationCard>) getArguments().getSerializable("cards");
        RecyclerView recyclerView = view.findViewById(R.id.showDestCardsRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new GameAdapter(getContext(), destinationCards);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
