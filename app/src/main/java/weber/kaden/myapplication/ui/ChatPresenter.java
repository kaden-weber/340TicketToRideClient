package weber.kaden.myapplication.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.ChatMessage;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class ChatPresenter implements Observer {

    private ChatFragment view;
    private ClientFacade client;
    private Model model;

    public ChatPresenter(ChatFragment view, ClientFacade client) {
        this.view = view;
        this.client = client;
        model = Model.getInstance();
    }

    public void sendMessage(String message) {
        String userId = model.getCurrentUser();
        Game currentGame = model.getCurrentGame();
        String gameId = currentGame.getID();
        try {
            client.sendMessage(gameId, userId, message);
        } catch (Exception e) {
            view.printChatError(e.getMessage());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ArrayList<?>
                && (!((ArrayList<?>)arg).isEmpty())
                && ((ArrayList<?>)arg).get(0) instanceof ChatMessage) {
            List<ChatMessage> chatMessages = (ArrayList<ChatMessage>) arg;
            view.updateChat(chatMessages);
        }
    }
}
