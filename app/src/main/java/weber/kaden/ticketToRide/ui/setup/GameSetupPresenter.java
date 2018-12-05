package weber.kaden.ticketToRide.ui.setup;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.model.ClientFacade;

public class GameSetupPresenter implements Observer {
    private FragmentSetup activity;
    private ClientFacade client;
    private boolean gotTravelRate = false;
    private boolean startedCardSelection = false;

    public GameSetupPresenter(FragmentSetup view, ClientFacade client) {
        this.activity = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    public void setGotTravelRate(boolean gotTravelRate) {
        this.gotTravelRate = gotTravelRate;
    }

    public boolean gotTravelRate() {
        return gotTravelRate;
    }

    public void sendNumPlaces(String gameId, String playerId, String numPlaces) throws Exception{
        client.setTravelRateByPlayer(gameId, playerId, numPlaces);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Game && ((Game) arg).isDestinationCardsDealt() && gotTravelRate() && !startedCardSelection) {
           activity.startCardSelection();
           startedCardSelection = true;
        }
    }
}
