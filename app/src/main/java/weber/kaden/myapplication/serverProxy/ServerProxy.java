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

        //TODO: Figure out a way to set up the server port and ip address
        ccom.setServerIP("0.0.0.0");
        ccom.setServerPort("8080");
    }

    public Results sendCommand(CommandData requestInfo) {
        //TODO: setup permissions for maybe separate send methods?
        return ccom.send(requestInfo, RequestType.POST);
    }
}
