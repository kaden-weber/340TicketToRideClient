package weber.kaden.myapplication.ui;

import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.ui.turnmenu.ChooseDestinationCardsFragment;

public class FragmentSetup extends DialogFragment {
    Button submit;
    FragmentSetup instance = this;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_setup_question, container, false);
        editText = view.findViewById(R.id.numPlacesText);
        submit = view.findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNumberPlaces sendNumberPlaces = new SendNumberPlaces(editText.getText().toString());
                sendNumberPlaces.execute();
            }
        });

        return view;
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
                android.support.v4.app.DialogFragment chooseCards = new ChooseInitialDestinationFragment();

                Bundle args = new Bundle();
                List<DestinationCard> dealtCards = new ClientFacade().getDealtDestinationCardsForCurrentPlayer();
                if (dealtCards.size() == 0) {
                    dealtCards = Model.getInstance().getGame(Model.getInstance().getCurrentGame().getID()).getTopOfDestinationCardDeck();
                }
                args.putSerializable("cards", (Serializable) dealtCards);
                chooseCards.setArguments(args);

                chooseCards.show(getFragmentManager(), "ChooseCardFragment");
            }else {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}
