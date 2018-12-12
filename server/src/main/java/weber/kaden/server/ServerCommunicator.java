package weber.kaden.server;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

        String persistenceType = args[1]; //args[1];
        int number_of_checkpoints = Integer.valueOf(args[2]);//args[2]

        PersistenceManager.getInstance().setDeltaValue(number_of_checkpoints);
        assignPersistencePlugin(persistenceType);

        // TODO: assign persistence stuff to Model here
        PersistenceManager.getInstance().loadFromDB();

        new ServerCommunicator().run(portNumber);
    }

    private static void assignPersistencePlugin(String pluginName){
        String directory = "server/plugins/";
        String jarName = pluginName + ".jar";
        String className = "com.example." + pluginName.toLowerCase() + "." + "DaoFactory";
        File pluginJarFile = new File(directory + jarName);
        try {
            URL pluginURL = pluginJarFile.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});
            Class<? extends DaoFactory> factoryClass = (Class<DaoFactory>) loader.loadClass(className);
            PersistenceManager.getInstance().setDaoFactory(factoryClass.getDeclaredConstructor(null).newInstance());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
