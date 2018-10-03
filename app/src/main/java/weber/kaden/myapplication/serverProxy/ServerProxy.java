package weber.kaden.myapplication.serverProxy;

import weber.kaden.common.Results;
import weber.kaden.common.command.CommandData;

public class ServerProxy {
    private static ServerProxy single_instance = null;
    private ClientCommunicator ccom;


    public static ServerProxy getInstance() {
        if (single_instance == null) {
            single_instance = new ServerProxy();
        }

        return single_instance;
    }

    private ServerProxy() {
        ccom = ClientCommunicator.getInstance();
    }

    public Results sendCommand(CommandData requestInfo) {
        switch (requestInfo.getType()) {
	        case LOGIN:
	        case REGISTER:
	        case CREATEGAME:
	        case JOINGAME:
	        case STARTGAME:
	        	return ccom.send(requestInfo, RequestType.POST);
            // When implementing new commands, decide whether they are post or get
        }

        return null;
    }
}
