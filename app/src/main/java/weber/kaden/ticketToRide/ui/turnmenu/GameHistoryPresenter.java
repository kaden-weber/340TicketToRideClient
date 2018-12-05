package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.model.ClientFacade;

public class GameHistoryPresenter implements Observer {

    private GameHistoryFragment mGameHistoryFragment;
    private ClientFacade client;
    private List<CommandData> commands;

    public GameHistoryPresenter(GameHistoryFragment mGameHistoryFragment, ClientFacade client) {
        this.mGameHistoryFragment = mGameHistoryFragment;
        this.client = client;
        this.commands = new ArrayList<CommandData>();
        Model.getInstance().addObserver(this);
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    public List<CommandData> getCommands() {
        return this.commands;
    }

    public void setCommands(List<CommandData> commands) {
        this.commands = commands;
    }
}
