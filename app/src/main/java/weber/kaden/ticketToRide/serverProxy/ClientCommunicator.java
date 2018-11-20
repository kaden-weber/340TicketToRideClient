package weber.kaden.ticketToRide.serverProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import weber.kaden.common.Serializer;
import weber.kaden.common.StreamProcessor;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.Results;

public class ClientCommunicator {
    private static ClientCommunicator single_instance = null;

    private String serverIP = "10.0.2.2";
    private String serverPort = "8080";


    public static ClientCommunicator getInstance() {
        if (single_instance == null) {
            single_instance = new ClientCommunicator();
        }

        return single_instance;
    }

    private ClientCommunicator() {
//        setServerIP(ServerCommunicationInfo.getServerIPAddress());
//        setServerPort(ServerCommunicationInfo.getServerPort());

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
		    StreamProcessor.writeString(serializedData, reqBody);
		    reqBody.close();

		    conn.connect();

		    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			    InputStream responseBody = conn.getInputStream();

			    String output = StreamProcessor.getString(responseBody);

			    responseBody.close();
			    conn.disconnect();

			    return serializer.deserializeResults(output, requestInfo.getType());
		    }

		    conn.disconnect();
	    }
	    catch (IOException e) {
    		e.printStackTrace();
	    }

	    return null;
    }
}