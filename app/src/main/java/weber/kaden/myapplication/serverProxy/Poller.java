package weber.kaden.myapplication.serverProxy;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.Toast;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import weber.kaden.common.Results;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.ui.GameListActivity;
import weber.kaden.myapplication.ui.LoginPresenter;

public class Poller {
	private static final long FIVE_SECONDS = 5000;
	private static Poller mPoller;

	public static Poller getInstance() {
		if (mPoller == null) {
			mPoller = new Poller(FIVE_SECONDS);
		}
		return mPoller;
	}
    private ClientCommunicator ccom;
    private ClientFacade clientFacade;
    private long waitTime;

    private Thread runningGamesListThread;
    private Thread runningGameThread;
    private final boolean runGamesList = true;
    private final boolean runGame = true;
    private final Queue<List<Game>> gamesList;
    private final Queue<Game> updatedGames;

    private Poller(long milliseconds) {
        waitTime = milliseconds;
        ccom = ClientCommunicator.getInstance();
        clientFacade = new ClientFacade();
        gamesList = new ConcurrentLinkedQueue<>();
        updatedGames = new ConcurrentLinkedQueue<>();
    }

    public void startGamesPolling() {
    	stopPolling();

    	if (runningGameThread == null) {
		    runningGameThread = new GameCommandsGetter();
	    }
		runningGameThread.start();
    }

    public void startGamesListPolling() {
        stopPolling();

    	if (runningGamesListThread == null) {
		    runningGamesListThread = new GamesListGetter();
	    }

    	runningGamesListThread.start();
    }

    public void stopGamesListPolling() {
		if (runningGamesListThread != null) {
			runningGamesListThread.interrupt();
		}
    }

    public void stopGamesPolling() {
    	if (runningGameThread != null) {
    	    runningGameThread.interrupt();
        }
    }

    public void stopPolling() {
        stopGamesListPolling();
        stopGamesPolling();
    }

    private class GamesListGetter extends Thread {

        @Override
	    public void run() {
    		while(!this.isInterrupted()) {
    			try {
				    Thread.sleep(waitTime);
				    List<Game> newGamesList = clientFacade.getGames();
                    //gamesList.add(newGamesList);
                    Model.getInstance().setGames(newGamesList);
			    }
			    catch (Exception e) {
    				e.printStackTrace();
			    }
		    }
	    }
    }

    private class GameCommandsGetter extends Thread {

        @Override
        public void run() {
        	while (!this.isInterrupted()) {
        		try {
			        Thread.sleep(waitTime);
					Game updatedGame = clientFacade.getUpdatedGame(Model.getInstance().getCurrentGame());
					//updatedGames.add(updatedGame);
		        }
		        catch (Exception e) {
        			e.printStackTrace();
		        }
	        }
        }
    }

}
