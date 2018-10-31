package weber.kaden.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.myapplication.R;

public class ChooseInitialDestinationFragment extends DialogFragment {
    //widgets
    private Button mActionCancel, mActionOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_choose_destination_cards, container, false);
        mActionCancel = view.findViewById(R.id.choose_destination_cancel);
        mActionOk = view.findViewById(R.id.choose_destination_ok);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        mActionCancel.setEnabled(false);

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Make this send data back
                getDialog().dismiss();
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        List<DestinationCard> dealtCards = (List<DestinationCard>) getArguments().getSerializable("cards");
        List<Boolean> chosenCards = Arrays.asList(false, false, false);
        Button mDestinationCard0 = view.findViewById(R.id.destination_card_0);
        Button mDestinationCard1 = view.findViewById(R.id.destination_card_1);
        Button mDestinationCard2 = view.findViewById(R.id.destination_card_2);

        String destination0Text = dealtCards.get(0).getStartCity().toString() + "\nTo\n" + dealtCards.get(0).getEndCity().toString();
        mDestinationCard0.setText(destination0Text);

        String destination1Text = dealtCards.get(1).getStartCity().toString() + "\nTo\n" + dealtCards.get(1).getEndCity().toString();
        mDestinationCard1.setText(destination1Text);

        String destination2Text = dealtCards.get(2).getStartCity().toString() + "\nTo\n" + dealtCards.get(2).getEndCity().toString();
        mDestinationCard2.setText(destination2Text);

        return view;
    }
}
