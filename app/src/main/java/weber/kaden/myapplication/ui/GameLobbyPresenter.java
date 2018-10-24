package weber.kaden.myapplication.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class GameLobbyPresenter implements Observer {

    private GameLobbyViewInterface activity;
    private ClientFacade client;

    public GameLobbyPresenter(GameLobbyActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    public void exitLobby() throws Exception {
        if (client.getCurrentGame() == null) {
            throw new Exception("Not currently in a game");
        } else if (client.getCurrentUser() == null) {
            throw new Exception("Error leaving game");
        }
        client.exitLobby(client.getCurrentUser(), client.getCurrentGame().getID());
    }

    public void startGame() throws Exception {
        if (client.getCurrentGame() == null) {
            throw new Exception("Not currently in a game");
        } else if (client.getCurrentUser() == null) {
            throw new Exception("Error starting game");
        }
        client.startGame(client.getCurrentUser(), client.getCurrentGame().getID());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Game && ((Game) arg).isStarted()) {
            activity.startGame();
        }
    }
}
