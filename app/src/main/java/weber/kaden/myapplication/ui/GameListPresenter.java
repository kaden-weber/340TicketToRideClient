package weber.kaden.myapplication.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.Results;
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
        return model.getGames();
    }

    public Game createGame(String username) throws Exception {
        System.out.println("creating game in presenter");
        Results results = client.createGame(username);
        return (Game) results.getData();
    }

    public void joinGame(String username, String gameID) throws Exception {
        client.joinGame(username, gameID);
    }


    @Override
    public void update(Observable o, Object arg) {
        // activity.updateGames((List<Game> arg);
    }
}
