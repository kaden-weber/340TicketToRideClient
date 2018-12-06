package weber.kaden.ticketToRide.serverProxy;

import weber.kaden.common.results.Results;
import weber.kaden.common.command.CommandData.CommandData;

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
	    return ccom.send(requestInfo, RequestType.POST);
    }
}
