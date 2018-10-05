package weber.kaden.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import weber.kaden.common.Results;
import weber.kaden.common.Serializer;
import weber.kaden.common.StreamProcessor;
import weber.kaden.common.command.CommandData;

import static java.net.HttpURLConnection.HTTP_OK;

public class ExecHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Connection received!");
        Serializer serializer = new Serializer();
        StreamProcessor streamProcessor = new StreamProcessor();
        String requestString = streamProcessor.getString(exchange.getRequestBody());
        try {
            CommandData commandData = serializer.deserializeCommandData(requestString);
            Results results = CommandManager.getInstance().processCommand(commandData);
            exchange.sendResponseHeaders(HTTP_OK, 0);
            String serializedResults = serializer.serializeResults(results);
            streamProcessor.writeString(serializedResults, exchange.getResponseBody());
            exchange.close();
        } catch (Exception e) {
            throw new IOException();
        }
    }

}
