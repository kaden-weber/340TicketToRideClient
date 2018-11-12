package weber.kaden.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import weber.kaden.common.Results;
import weber.kaden.common.Serializer;
import weber.kaden.common.StreamProcessor;
import weber.kaden.common.command.CommandData.CommandData;

import static java.net.HttpURLConnection.HTTP_OK;

public class ExecHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
	    System.out.print("[" + new SimpleDateFormat("HH.mm.ss").format(new Date()) + "] " + "Connection received: ");

        Serializer serializer = new Serializer();
        String requestString = StreamProcessor.getString(exchange.getRequestBody());

        try {
            CommandData commandData = serializer.deserializeCommandData(requestString);

            System.out.println(commandData.getType().toString() + " Command");

            Results results = CommandManager.getInstance().processCommand(commandData);
            exchange.sendResponseHeaders(HTTP_OK, 0);
            String serializedResults = serializer.serializeResults(results);
            StreamProcessor.writeString(serializedResults, exchange.getResponseBody());
            exchange.close();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

}
