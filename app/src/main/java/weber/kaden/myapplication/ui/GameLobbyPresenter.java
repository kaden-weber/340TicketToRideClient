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

    public void exitLobby(String username) throws Exception {
        //client.exitLobby(userName);
    }

    public void startGame() throws Exception {
        //client.startGame();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
