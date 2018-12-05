package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.ui.setup.GameSetupActivity;
import weber.kaden.ticketToRide.ui.tools.ConstantsDisplayConverter;

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
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        //string converter tool
        ConstantsDisplayConverter converter = new ConstantsDisplayConverter();

        final List<DestinationCard> dealtCards = (List<DestinationCard>) getArguments().getSerializable("cards");
        final List<Boolean> chosenCards = Arrays.asList(false, false, false);
        ToggleButton mDestinationCard0 = view.findViewById(R.id.destination_card_0);
        ToggleButton mDestinationCard1 = view.findViewById(R.id.destination_card_1);
        ToggleButton mDestinationCard2 = view.findViewById(R.id.destination_card_2);

        String destination0Text = converter.getUIStringFor(dealtCards.get(0).getStartCity()) + "\nTo\n" + converter.getUIStringFor(dealtCards.get(0).getEndCity());
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

        String destination1Text = converter.getUIStringFor(dealtCards.get(1).getStartCity()) + "\nTo\n" + converter.getUIStringFor(dealtCards.get(1).getEndCity());
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

        String destination2Text = converter.getUIStringFor(dealtCards.get(2).getStartCity()) + "\nTo\n" + converter.getUIStringFor(dealtCards.get(2).getEndCity());
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
                SendChosenCards sendChosenCards = new SendChosenCards(clientFacade.getCurrentUserID(),
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
                    GameSetupActivity setupActivity = (GameSetupActivity) getActivity();
                    setupActivity.startGameActivity();
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
