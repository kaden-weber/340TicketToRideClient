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

    public void startPolling() {
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

			        CommandData commandData = new CommandData(null, CommandType.GETCOMMANDS);
			        Results results = ccom.send(commandData, RequestType.GET);

			        if (results.success()) {
				        List<CommandData> commandsList = (List<CommandData>) results.getData();

				        for (int i = 0; i < commandsList.size(); i++) {
				        	CommandData indexedCommandData = commandsList.get(i);
							switch (indexedCommandData.getType()) {
								case LOGIN:
									new ClientFacade().login(indexedCommandData.getData().get(0), indexedCommandData.getData().get(1));
									break;
								case REGISTER:
									new ClientFacade().register(indexedCommandData.getData().get(0), indexedCommandData.getData().get(1));
									break;
							}
				        }
			        }
		        }
		        catch (Exception e) {
        			e.printStackTrace();
		        }
	        }
        }
    }
}
