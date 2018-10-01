package weber.kaden.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer httpServer;

    private void run(String portNumber){
        System.out.println("Initializing Http server");
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
        System.out.println(httpServer.getAddress().getHostName());

    }

    public static void main(String[] args){
        String portNumber = args[0];
        new ServerCommunicator().run(portNumber);
    }

}
