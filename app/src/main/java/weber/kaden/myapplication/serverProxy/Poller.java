package weber.kaden.myapplication.serverProxy;

import android.app.Activity;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class Poller {
	private static final long DELAY = 3000; //1000 equals 1 second
	private static Poller mPoller;
	private Activity callingActivity;

	public static Poller getInstance(Activity callingActivity) {
		if (mPoller == null) {
			mPoller = new Poller(DELAY);
		}
		mPoller.callingActivity = callingActivity;
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

    	runningGameThread = new GameCommandsGetter();
		runningGameThread.start();
    }

    public void startGamesListPolling() {
        stopPolling();

    	runningGamesListThread = new GamesListGetter();
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

				    callingActivity.runOnUiThread(new Runnable() {
					    List<Game> newGamesList = clientFacade.getGames();

					    @Override
					    public void run() {
						    Model.getInstance().setGames(newGamesList);
					    }
				    });

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
					callingActivity.runOnUiThread(new Runnable() {
						Game updatedGame = clientFacade.getUpdatedGame(Model.getInstance().getCurrentGame());

						@Override
						public void run() {
							Model.getInstance().updateGame(updatedGame);
						}
					});
		        }
		        catch (Exception e) {
        			e.printStackTrace();
		        }
	        }
        }
    }

}
