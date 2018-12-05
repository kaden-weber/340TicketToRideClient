package weber.kaden.ticketToRide.ui.chat;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.model.ClientFacade;

/**
 * Presenter that processes information sent between the chat view and the model
 *
 * @invariant view != null
 * @invariant client != null
 */
public class ChatPresenter implements Observer {

    private ChatFragment view;
    private ClientFacade client;
    private Model model;

    /**
     * Constructor for the ChatPresenter class.
     *
     * @param view      the view that this presenter is associated with
     * @param client    a reference to the ClientFacade to access the client's model
     *
     * @pre view != null
     * @pre client != null
     *
     * @post this != null
     */
    public ChatPresenter(ChatFragment view, ClientFacade client) {
        this.view = view;
        this.client = client;
        model = Model.getInstance();
        model.addObserver(this);
    }

    /**
     * Receives a message from the view and packages it as a chat message to be sent to the server.
     *
     * @param message   the message to send to the server
     *
     * @pre !message.isEmpty() && message != null
     *
     * @post message is unchanged
     */
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

    /**
     * update method required by the Observer interface. Receives and processes updated game information from the
     * model and sends the chat messages to the view for display.
     *
     * @param o     reference to this Observer's Observable
     * @param arg   Game object containing the updated chat messages
     *
     * @pre arg instanceof Game
     *
     * @post arg.getChat() contains the updated chat
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Game) {
            view.updateChat(((Game) arg).getChat());
        }
    }
}
