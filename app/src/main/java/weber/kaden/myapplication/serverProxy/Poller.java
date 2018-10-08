package weber.kaden.myapplication.serverProxy;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandType;
import weber.kaden.myapplication.model.ClientFacade;

public class Poller {
    private ClientCommunicator ccom;
    private long waitTime;

    private Thread runningThread;

    public Poller(long miliseconds) {
        waitTime = miliseconds;
        ccom = ClientCommunicator.getInstance();
    }

    public void startGamesListPolling() {
		runningThread = new CommandsGetter();
		runningThread.start();
    }

    public void stopPolling() {
		if (runningThread != null) {
			runningThread.stop();
		}
    }

    private class CommandsGetter extends Thread {

        @Override
        public void run() {
        	while (true) {
        		try {
			        Thread.sleep(waitTime);


		        }
		        catch (Exception e) {
        			e.printStackTrace();
		        }
	        }
        }
    }
}
