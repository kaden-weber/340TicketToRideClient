package weber.kaden.myapplication.ui;

import android.view.Display;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class GameListPresenter implements Observer {
    private GameListActivity activity;
    private ClientFacade client;
    private Model model = Model.getInstance();
    public GameListPresenter(GameListActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

    public List<Game> displayGames() {
        return client.getGames();
    }
    /*
    public void createGame(String username) throws Exception {
        client.createGame(username);
    }

    public void joinGame(String username) throws Exception {
        client.joinGame(username);
    } */


    @Override
    public void update(Observable o, Object arg) {

    }
}
