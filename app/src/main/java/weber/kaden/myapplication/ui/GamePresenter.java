package weber.kaden.myapplication.ui;

import weber.kaden.myapplication.model.ClientFacade;

public class GamePresenter {
    private GameViewInterface view;
    private ClientFacade client;

    public GamePresenter(GameViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
    }
}
