package weber.kaden.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private void run(String portNumber){
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
        httpServer.createContext("/", new ExecHandler());
        System.out.println("Starting server");
        httpServer.start();
        System.out.println("Server started");
    }

    public static void main(String[] args){
        String portNumber = args[0];
        new ServerCommunicator().run(portNumber);
    }

}
