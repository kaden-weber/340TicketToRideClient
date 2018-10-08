package weber.kaden.myapplication.serverProxy;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

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

    private Thread runningThread;

    private Poller(long milliseconds) {
        waitTime = milliseconds;
        ccom = ClientCommunicator.getInstance();
        clientFacade = new ClientFacade();
    }

    public void startGamesPolling() {
    	stopPolling();

		runningThread = new GameCommandsGetter();
		runningThread.start();
    }

    public void startGamesListPolling() {
    	stopPolling();

    	runningThread = new GamesListGetter();
    	runningThread.start();
    }

    public void stopPolling() {
		if (runningThread != null) {
			runningThread.stop();
		}
    }

    private class GamesListGetter extends Thread {

    	@Override
	    public void run() {
    		while(true) {
    			try {
				    Thread.sleep(waitTime);

				    List<Game> newGamesList = clientFacade.getGames();
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
        	while (true) {
        		try {
			        Thread.sleep(waitTime);

					//TODO: When we implement playing games, implement this
		        }
		        catch (Exception e) {
        			e.printStackTrace();
		        }
	        }
        }
    }
}
