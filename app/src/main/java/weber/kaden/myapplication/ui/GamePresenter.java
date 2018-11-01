package weber.kaden.myapplication.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.City;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.myapplication.model.ClientFacade;

public class GamePresenter implements Observer {
    private GameViewInterface view;
    private ClientFacade client;

    public GamePresenter(GameViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    public List<TrainCard> getFaceUpTrainCards() {

    	return null;
    }

    public List<TrainCard> getTrainCardsDeck() {

    	return null;
    }

    public boolean drawFaceUpTrainCard(TrainCard drawnCard) {

    	return false;
    }

    public boolean drawTrainCardFromDeck() {

    	return false;
    }

    public List<DestinationCard> getDrawableDestinationCards() {

        return new ClientFacade().getDestinationCardsForTurn();
    }

    public void chooseDestinationCards(String username, String gameId, List<DestinationCard> drawnCards, List<DestinationCard> discardedCards) throws Exception{
        client.sendDestinationCards(username, gameId, drawnCards, discardedCards);
    }

    @Override
    public void update(Observable observable, Object o) {
        //update claimed routes

    }

    public void runPhase2Test() {
        //TODO Run test list
        //TODO: Run in an asynctask so there can be pauses?
        view.sendMessage("Running Tests");

        view.sendMessage("Adding to your train cards");
        //client.drawTrainCard(client.getCurrentUser());
        //delay
        view.sendMessage("Removing one of your train cards");
//        client.removeTrainCardFromPlayer(client.getCurrentUser()); //??
        view.sendMessage("Adding to your destination cards");
//        client.drawDestinationCard(client.getCurrentUser());
        view.sendMessage("Removing one of your destination cards");
//        client.removeDestinationCardFromPlayer(client.getCurrentUser()); //??
        view.sendMessage("Updating opponent train cards");
//        client.dealTrainCardsToOpponents();
        view.sendMessage("Updating opponent train cars");
//        client.changeOpponentsTrainCars();
        view.sendMessage("Updating opponent destination cards");
//        client.dealDestinationCardsToOpponents();
        view.sendMessage("Updating number of visible train cards in deck");
        //don't know what to do for these
        view.sendMessage("Updating number of invisible train cards in deck");
        //ditto
        view.sendMessage("Updating number of destination cards in deck");
        //ditto
        view.sendMessage("Adding a claimed route: Calgary-Winnipeg");
//        client.haveOpponentClaimRoute(new Route(City.CALGARY, City.WINNIPEG, 6, TrainCardType.PASSENGER));
        view.sendMessage("Adding a chat message");
//        ChatPresenter chatPresenter = new ChatPresenter(mock, ); // Looks like we need mocks for this
//        chatPresenter.sendMessage("Hey guys!");

        view.sendMessage("Advancing turn to next player");
//        client.endTurn(client.getCurrentUser());


//  For each change, the presenter should:
//  o Call the view to display a toast describing the change (e.g., “Updating player points”).
//  o Make the indicated change to the local client model (no server interaction is
//        involved here). This should cause your client model to notify presenters of
//        the model changes, and presenters should update their views appropriately.
//  o The following items should be demonstrated:
//      ● Update player points
//      ● Add/remove train cards for this player
//      ● Add/remove player destination cards for this player
//      ● Update the number of train cards for opponent players
//      ● Update the number of train cars for opponent players
//      ● Update the number of destination cards for opponent players
//      ● Update the visible (face up) cards in the train card deck
//      ● Update the number of invisible (face down) cards in train card deck
//      ● Update the number of cards in destination card deck
//      ● Add claimed route (for any player). Show this on the map.
//      ● Add chat message from any player
//      ● Advance player turn (change the turn indicator so it indicates another
//                player)
//  o Include pauses as needed to allow the TA to see each change.
//  o Each of the capabilities must be implemented by modifying the client model
//        which in return uses the observable to notify the presenters (controllers),
//                which update the views.


    }
}
