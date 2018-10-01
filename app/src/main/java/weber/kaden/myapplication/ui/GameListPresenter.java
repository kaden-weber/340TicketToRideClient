package weber.kaden.myapplication.ui;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.myapplication.model.ClientFacade;

public class GameListPresenter implements Observer {
    private GamesListActivity activity;
    private ClientFacade client;

    public GameListPresenter(GamesListActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

//    public List<model.Game>displayGames() {
//        return client.getGames();
//    }
//
//    public void createGame(String username) throws Exception {
//        client.createGame(username);
//    }
//
//    public void joinGame(String username) throws Exception {
//        client.joinGame(username);
//    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
