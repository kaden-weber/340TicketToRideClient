package weber.kaden.ticketToRide.ui.chat;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChatPresenter implements Observer {

    private ChatFragment view;
    private ClientFacade client;
    private Model model;

    public ChatPresenter(ChatFragment view, ClientFacade client) {
        this.view = view;
        this.client = client;
        model = Model.getInstance();
        model.addObserver(this);
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
        if (arg instanceof Game) {
            view.updateChat(((Game) arg).getChat());
        }
    }
}
