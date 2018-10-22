package weber.kaden.myapplication.serverProxy;

import android.app.Activity;

import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class Poller {
	private static final long DELAY = 1000; //1000 equals 1 second
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

    private GamesListGetter gamesListThread;
    private GameGetter gameThread;

    private PollerState currentState;

    private Poller(long milliseconds) {
        waitTime = milliseconds;
        ccom = ClientCommunicator.getInstance();
        clientFacade = new ClientFacade();
        currentState = null;
    }

    public void pollGame() {
    	currentState.pollGame();
    }

    public void pollGamesList() {
    	currentState.pollGamesList();
    }

    public void setState(PollerState state) {
		if (currentState != null) {
			currentState.exit();
		}

		currentState = state;

		if (currentState != null) {
			currentState.enter();
		}
    }

    private class GamesListGetter extends Thread {
    	public boolean isGamesListThreadRunning = true;

        @Override
	    public void run() {
    		while(isGamesListThreadRunning) {
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

    private class GameGetter extends Thread {
    	public boolean isGameThreadRunning = true;

        @Override
        public void run() {
        	while (isGameThreadRunning) {
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

	private abstract class PollerState {

		//called when a state is entered
		public abstract void enter();

		//called when a state is exited
		public abstract void exit();

		//called when the user logs in
		public abstract void pollGamesList();

		//called when the user joins a game
		public abstract void pollGame();
	}

	private class PollGameState extends PollerState {
		@Override
		public void enter() {
			gameThread = new GameGetter();
		}

		@Override
		public void exit() {
			if (gameThread != null) {
				gameThread.isGameThreadRunning = false;
				gameThread = null;
			}
		}

		@Override
		public void pollGamesList() {
			setState(new PollGamesListState());
			currentState.pollGamesList();
		}

		@Override
		public void pollGame() {
			gameThread.start();
		}
	}

	private class PollGamesListState extends PollerState {
		@Override
		public void enter() {
			gamesListThread = new GamesListGetter();
		}

		@Override
		public void exit() {
			if (gamesListThread != null) {
				gamesListThread.isGamesListThreadRunning = false;
				gamesListThread = null;
			}
		}

		@Override
		public void pollGamesList() {
			gamesListThread.start();
		}

		@Override
		public void pollGame() {
			setState(new PollGameState());
			currentState.pollGame();
		}
	}

}
