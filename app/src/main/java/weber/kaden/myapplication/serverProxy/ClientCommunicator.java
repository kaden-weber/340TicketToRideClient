package weber.kaden.myapplication.serverProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import weber.kaden.common.Serializer;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.Results;

public class ClientCommunicator {
    private static ClientCommunicator single_instance = null;

    private String serverIP = "10.0.2.2";//"13.59.120.155";
    private String serverPort = "8080";


    public static ClientCommunicator getInstance() {
        if (single_instance == null) {
            single_instance = new ClientCommunicator();
        }

        return single_instance;
    }

    private ClientCommunicator() {
        setServerIP(ServerCommunicationInfo.getServerIPAddress());
        setServerPort(ServerCommunicationInfo.getServerPort());
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Results send(CommandData requestInfo, RequestType requestType) {
    	try {
		    URL url = new URL("http://" + serverIP + ":" + serverPort + "/");
		    Serializer serializer = new Serializer();
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    switch (requestType) {
			    case GET:
			    	conn.setRequestMethod("GET");
			    	break;
			    case POST:
			    	conn.setRequestMethod("POST");
			    	break;
		    }
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setConnectTimeout(5000);

		    OutputStream reqBody = conn.getOutputStream();
		    String serializedData = serializer.serializeCommandData(requestInfo);
		    reqBody.write(serializedData.getBytes());
		    reqBody.close();

		    conn.connect();

		    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			    InputStream responseBody = conn.getInputStream();

			    BufferedReader in = new BufferedReader(new InputStreamReader(responseBody));

			    String inputLine;
			    String output = "";
			    while ((inputLine = in.readLine()) != null) {
				    output += inputLine;
			    }

			    responseBody.close();
			    conn.disconnect();

			    return serializer.deserializeResults(output);
		    }
	    }
	    catch (IOException e) {
    		e.printStackTrace();
	    }

	    return null;
    }
}