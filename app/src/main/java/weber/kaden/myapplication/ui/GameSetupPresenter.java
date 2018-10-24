package weber.kaden.myapplication.ui;

import weber.kaden.myapplication.model.ClientFacade;

public class GameSetupPresenter {
    private GameSetupViewInterface view;
    private ClientFacade client;

    public GameSetupPresenter(GameSetupViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
    }
}
