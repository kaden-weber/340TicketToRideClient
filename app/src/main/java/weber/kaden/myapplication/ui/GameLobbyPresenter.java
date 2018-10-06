package weber.kaden.myapplication.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.myapplication.model.ClientFacade;

public class GameLobbyPresenter implements Observer {

    private GameLobbyActivity activity;
    private ClientFacade client;

    public GameLobbyPresenter(GameLobbyActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

    public void exitLobby(String username, String gameID) throws Exception {
        client.exitLobby(username, gameID);
    }

    public void startGame(String username, String gameID) throws Exception {
        client.startGame(username, gameID);
    }

    @Override
    public void update(Observable o, Object arg) {
        //activity.updateGame((List<Game>) arg);
    }
}
