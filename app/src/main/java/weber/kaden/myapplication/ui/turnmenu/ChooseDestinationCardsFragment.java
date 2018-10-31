package weber.kaden.myapplication.ui.turnmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.ui.GameActivity;
import weber.kaden.myapplication.ui.GamePresenter;
import weber.kaden.myapplication.ui.GameViewInterface;

public class ChooseDestinationCardsFragment extends DialogFragment implements GameViewInterface {
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

        final List<DestinationCard> dealtCards = (List<DestinationCard>) getArguments().getSerializable("cards");
        final List<Boolean> chosenCards = Arrays.asList(false, false, false);
        ToggleButton mDestinationCard0 = view.findViewById(R.id.destination_card_0);
        ToggleButton mDestinationCard1 = view.findViewById(R.id.destination_card_1);
        ToggleButton mDestinationCard2 = view.findViewById(R.id.destination_card_2);

        String destination0Text = dealtCards.get(0).getStartCity().toString() + "\nTo\n" + dealtCards.get(0).getEndCity().toString();
        mDestinationCard0.setTextOn(destination0Text);
        mDestinationCard0.setTextOff(destination0Text);
        mDestinationCard0.setText(destination0Text);
        mDestinationCard0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chosenCards.set(0, true);
                }
                else {
                    chosenCards.set(0, false);
                }
            }
        });

        String destination1Text = dealtCards.get(1).getStartCity().toString() + "\nTo\n" + dealtCards.get(1).getEndCity().toString();
        mDestinationCard1.setTextOn(destination1Text);
        mDestinationCard1.setTextOff(destination1Text);
        mDestinationCard1.setText(destination1Text);
        mDestinationCard1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chosenCards.set(1, true);
                }
                else {
                    chosenCards.set(1, false);
                }
            }
        });

        String destination2Text = dealtCards.get(2).getStartCity().toString() + "\nTo\n" + dealtCards.get(2).getEndCity().toString();
        mDestinationCard2.setTextOn(destination2Text);
        mDestinationCard2.setTextOff(destination2Text);
        mDestinationCard2.setText(destination2Text);
        mDestinationCard2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chosenCards.set(2, true);
                }
                else {
                    chosenCards.set(2, false);
                }
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Make this send data back
                getDialog().dismiss();
                List<DestinationCard> chosenList = new ArrayList<>();
                List<DestinationCard> discardedList = new ArrayList<>();
                for (int i = 0; i < chosenCards.size(); i++) {
                    if (chosenCards.get(i)) {
                        chosenList.add(dealtCards.get(i));
                    }
                    else {
                        discardedList.add(dealtCards.get(i));
                    }
                }
                sendCards(chosenList, discardedList);
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void sendCards(List<DestinationCard> drawnCards, List<DestinationCard> discardedCards) {
        new GamePresenter(this, new ClientFacade()).chooseDestinationCards(drawnCards, discardedCards);
    }
}
