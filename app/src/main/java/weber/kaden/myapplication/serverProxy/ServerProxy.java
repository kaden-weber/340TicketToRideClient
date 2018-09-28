package weber.kaden.myapplication.serverProxy;

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

    public main.java.weber.kaden.common.Results sendCommand(main.java.weber.kaden.common.command.CommandData requestInfo) {
        //TODO: setup permissions for maybe seperate send methods?
        return ccom.send(requestInfo);
    }
}
