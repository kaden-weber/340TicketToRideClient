package weber.kaden.myapplication.ui.turnmenu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.ui.gameView.GameActivity;

public class ChooseDestinationCardsFragment extends DialogFragment {
    //widgets
    private ChooseDestinationCardsFragment instance = this;
    private Button mActionCancel, mActionOk;
    private int numCardsSelected;
    private boolean isSetup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_choose_destination_cards, container, false);
        isSetup = Model.getInstance().getCurrentGame().isSetup();
        setCancelable(false);
        mActionCancel = view.findViewById(R.id.choose_destination_cancel);
        mActionOk = view.findViewById(R.id.choose_destination_ok);
        mActionOk.setEnabled(false);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        mActionCancel.setEnabled(false);

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
                    numCardsSelected++;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
                }
                else {
                    chosenCards.set(0, false);
                    numCardsSelected--;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
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
                    numCardsSelected++;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
                }
                else {
                    chosenCards.set(1, false);
                    numCardsSelected--;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
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
                    numCardsSelected++;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
                }
                else {
                    chosenCards.set(2, false);
                    numCardsSelected--;
                    if (isSetup) mActionOk.setEnabled(numCardsSelected >= 2);
                    else mActionOk.setEnabled(numCardsSelected >= 1);
                }
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                ClientFacade clientFacade = new ClientFacade();
                SendChosenCards sendChosenCards = new SendChosenCards(clientFacade.getCurrentUser(),
                        clientFacade.getCurrentGame().getID(), chosenList, discardedList);
                sendChosenCards.execute();
            }
        });

        return view;
    }

    public class SendChosenCards extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String gameId;
        private final List<DestinationCard> cardsKept;
        private final List<DestinationCard> cardsDiscarded;

        private String errorMessage;

        SendChosenCards(String mUsername, String mgameId, List<DestinationCard> mCardsKept, List<DestinationCard> mCardsDiscarded) {
            username = mUsername;
            gameId = mgameId;
            cardsKept = mCardsKept;
            cardsDiscarded = mCardsDiscarded;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            ChooseDestinationCardsPresenter gamePresenter = new ChooseDestinationCardsPresenter(instance, clientFacade);
            try {
                gamePresenter.chooseDestinationCards(username, gameId, cardsKept, cardsDiscarded);
            } catch (Exception e) {
                errorMessage = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                getDialog().dismiss();

                if (isSetup) {
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    startActivity(intent);
                }
            }else {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}
