package weber.kaden.myapplication.serverProxy;

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

    //TODO: potentially have a way to differentiate whether it's a GET request or a POST request
    public main.java.weber.kaden.common.Results send(main.java.weber.kaden.common.command.CommandData requestInfo) {
        return null;
    }
}