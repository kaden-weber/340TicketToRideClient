package weber.kaden.server;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import weber.kaden.common.injectedInterfaces.persistence.DaoFactory;

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
        assignPersistencePlugin(persistenceType);


        new ServerCommunicator().run(portNumber);
    }

    private static void assignPersistencePlugin(String pluginName){
        // make directory name, jar name and class name
        String directory = "/" + pluginName;
        String jarName = pluginName + ".jar";
        String className = pluginName + "DaoFactory";

        // TODO: load plugin
        File pluginJarFile = new File(jarName);
        try {
            URL pluginURL = pluginJarFile.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});
            //Class<? extends DaoFactory> factoryClass = (Class<DaoFactory>);
            //TODO: get class name
            loader.loadClass(jarName);
            //return ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: assign persistence stuff to Model here
//        switch (persistenceType){
//            case "SQLite":
//                PersistenceManager.getInstance().setDaoFactory(DaoFactory);
//        }
        // PersistenceManager.getInstance.setFactory(FlatDaoFactory);
        // PersistenceManager.getInstance.setFactory(SQLDaoFactory);
        // PersistenceManager.loadFromDB();
    }

}
