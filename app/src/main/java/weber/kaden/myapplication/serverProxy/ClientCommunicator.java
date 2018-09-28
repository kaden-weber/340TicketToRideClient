package weber.kaden.myapplication.serverProxy;

import weber.kaden.common.command.CommandData;
import weber.kaden.common.Results;

public class ClientCommunicator {
    private static ClientCommunicator single_instance = null;

    private String serverIP;
    private String serverPort;

    public static ClientCommunicator getInstance() {
        if (single_instance == null) {
            single_instance = new ClientCommunicator();
        }

        return single_instance;
    }

    private ClientCommunicator() {
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Results send(CommandData requestInfo, RequestType requestType) {
        return null;
    }
}