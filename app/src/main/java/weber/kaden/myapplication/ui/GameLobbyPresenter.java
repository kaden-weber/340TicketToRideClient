package weber.kaden.myapplication.ui;

import weber.kaden.myapplication.model.ClientFacade;

public class GameLobbyPresenter {

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


}
