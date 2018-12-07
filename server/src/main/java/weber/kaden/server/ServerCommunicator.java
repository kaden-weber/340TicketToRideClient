package weber.kaden.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private void run(String portNumber, String persistenceType, int number_of_checkpoints){
        System.out.println("Initializing Http server");
        HttpServer httpServer;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        httpServer.setExecutor(null);

        System.out.println("Creating contexts");
        httpServer.createContext("/", new ExecHandler ());
        System.out.println("Starting server");
        httpServer.start();
        System.out.println("Server started");
    }

    public static void main(String[] args){
        String portNumber = args[0];
        // TODO: get plugin arguments
        String persistenceType = "";//args[1];
        int number_of_checkpoints = 0;//args[2]
        PersistenceManager.getInstance().setDeltaValue(number_of_checkpoints);
        // TODO: assign persistence stuff to Model here
        // PersistenceManager.getInstance.setFactory(FlatDaoFactory);
        // PersistenceManager.getInstance.setFactory(SQLDaoFactory);
        // PersistenceManager.loadFromDB();
        new ServerCommunicator().run(portNumber, persistenceType, number_of_checkpoints);
    }

}
