package weber.kaden.myapplication.ui.turnmenu;

import android.os.AsyncTask;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.command.CommandData;
import weber.kaden.myapplication.model.ClientFacade;

public class GameHistoryPresenter implements Observer {

    private GameHistoryFragment mGameHistoryFragment;
    private ClientFacade client;
    private List<CommandData> commands;

    public GameHistoryPresenter(GameHistoryFragment mGameHistoryFragment, ClientFacade client) {
        this.mGameHistoryFragment = mGameHistoryFragment;
        this.client = client;
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    public void getCommandsTask() {
        new getCommandsTask().execute();
    }

    public List<CommandData> getCommands() {
        return commands;
    }

    public void setCommands(List<CommandData> commands) {
        this.commands = commands;
    }

    private class getCommandsTask extends AsyncTask<Void, Void, Boolean> {
        private List<CommandData> data;

        @Override
        protected Boolean doInBackground(Void... voids) {
            data = client.getGameHistory();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setCommands(data);
        }
    }
}
