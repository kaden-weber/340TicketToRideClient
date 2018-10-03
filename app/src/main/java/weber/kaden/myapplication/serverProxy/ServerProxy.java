package weber.kaden.myapplication.serverProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		    case JOINGAME:
		    case STARTGAME:
		    case CREATEGAME:
			    return ccom.send(requestInfo, RequestType.POST);
		    case GETTURNS:
			    return ccom.send(requestInfo, RequestType.GET);
	    }
	    return null;
    }
}
