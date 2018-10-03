package weber.kaden.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import weber.kaden.common.Results;
import weber.kaden.common.Serializer;
import weber.kaden.common.StreamProcessor;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;

import static java.net.HttpURLConnection.HTTP_OK;

public class ExecHandler implements HttpHandler {

    private Serializer serializer;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        serializer = new Serializer();
        StreamProcessor streamProcessor = new StreamProcessor();
        String requestString = streamProcessor.getString(exchange.getRequestBody());
        try {
            Results results = processString(requestString);
            exchange.sendResponseHeaders(HTTP_OK, 0);
            String serializedResults = serializer.serializeResults(results);
            streamProcessor.writeString(serializedResults, exchange.getResponseBody());
            exchange.close();
        } catch (NumberFormatException e) {
            throw new IOException("Number");
        } catch (Exception e) {
            throw new IOException();
        }
    }

    private Results processString(String requestString) throws Exception {
        CommandData commandData = serializer.deserializeCommandData(requestString);
        Command command = CommandFactory.getCommand(commandData);
        return command.execute();
    }

}
