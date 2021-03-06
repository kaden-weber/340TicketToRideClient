package weber.kaden.ticketToRide.ui.setup;

import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.ui.turnmenu.ChooseDestinationCardsFragment;

public class FragmentSetup extends DialogFragment {
    Button submit;
    FragmentSetup instance = this;
    EditText editText;
    FragmentManager fm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_setup_question, container, false);
        setCancelable(false);
        editText = view.findViewById(R.id.numPlacesText);
        submit = view.findViewById(R.id.button);
        fm = getFragmentManager();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNumberPlaces sendNumberPlaces = new SendNumberPlaces(editText.getText().toString());
                sendNumberPlaces.execute();
            }
        });

        return view;
    }
    public void startCardSelection(){
        android.support.v4.app.DialogFragment chooseCards = new ChooseDestinationCardsFragment();

        Bundle args = new Bundle();
        List<DestinationCard> dealtCards = new ClientFacade().getDealtDestinationCardsForCurrentPlayer();
        if (dealtCards.size() == 0) {
            dealtCards = Model.getInstance().getGame(Model.getInstance().getCurrentGame().getID()).getTopOfDestinationCardDeck();
        }
        args.putSerializable("cards", (Serializable) dealtCards);
        chooseCards.setArguments(args);
        chooseCards.show(fm, "ChooseCardFragment");
    }
    public class SendNumberPlaces extends AsyncTask<Void, Void, Boolean> {

        private final String numPlaces;
        private String errorMessage;

        SendNumberPlaces(String mnumPlaces) {
            numPlaces = mnumPlaces;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameSetupPresenter gameSetupPresenter = new GameSetupPresenter(instance, clientFacade);
            try {
                gameSetupPresenter.sendNumPlaces(Model.getInstance().getCurrentGame().getID(), Model.getInstance().getCurrentUser(), numPlaces);
                gameSetupPresenter.setGotTravelRate(true);
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
            }else {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}
