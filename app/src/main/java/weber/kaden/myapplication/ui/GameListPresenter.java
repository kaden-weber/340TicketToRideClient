package weber.kaden.myapplication.ui;

import weber.kaden.myapplication.model.ClientFacade;

public class GameListPresenter {
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
}
