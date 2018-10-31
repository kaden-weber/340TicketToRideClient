package weber.kaden.myapplication.ui;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class GameSetupPresenter implements Observer {
    private FragmentSetup activity;
    private ClientFacade client;

    public GameSetupPresenter(FragmentSetup view, ClientFacade client) {
        this.activity = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }
    public void sendNumPlaces(String gameId, String playerId, String numPlaces) throws Exception{
        client.setTravelRateByPlayer(gameId, playerId, numPlaces);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Game && ((Game) arg).isStarted()) {
            //activity.startGameActivity();
        }
    }
}
