package weber.kaden.myapplication.ui;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class GameSetupPresenter implements Observer {
    private GameSetupActivity activity;
    private ClientFacade client;

    public GameSetupPresenter(GameSetupActivity view, ClientFacade client) {
        this.activity = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("In Game SETUP" + arg);
        if (arg instanceof Game && ((Game) arg).isStarted()) {
            activity.startGameActivity();
        }
    }
}
