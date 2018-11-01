package weber.kaden.myapplication.ui;

import android.os.AsyncTask;

import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import weber.kaden.common.model.City;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.myapplication.model.ClientFacade;

public class GamePresenter implements Observer {
    private GameViewInterface view;
    private ClientFacade client;
    private  GameActivity activity;
    public GamePresenter(GameViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }
    public GamePresenter(GameActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    boolean routeClaimable(int size, TrainCardType type){
        return client.PlayerCanClaimRoute(size, type);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof Game) {
            Game game = (Game) arg;
            List<DestinationCard> destCards = game.getPlayer(client.getCurrentUser()).getDestinationCardHand();
            List<TrainCard> trainCards = game.getPlayer(client.getCurrentUser()).getTrainCards();
            activity.setMyNewValues(destCards, trainCards);
        }
    }

    public class runPhase2TestTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            view.sendMessage("Running Tests");

            view.sendMessage("Adding to your train cards");
            client.testDrawTrainCard();
            delay();
            view.sendMessage("Removing one of your train cards");
            client.testRemoveTrainCardFromPlayer();
            delay();
            view.sendMessage("Adding to your destination cards");
            client.testDrawDestinationCards();
            delay();
            view.sendMessage("Removing one of your destination cards");
            client.testRemoveDestinationCardFromPlayer();
            delay();
            view.sendMessage("Updating opponent train cards");
            client.testDealTrainCardsToOpponents();
            delay();
            view.sendMessage("Updating opponent train cars");
            client.testChangeOpponentsTrainCars();
            delay();
            view.sendMessage("Updating opponent destination cards");
            client.testDealDestinationCardsToOpponents();
            delay();
            view.sendMessage("Updating number of visible train cards in deck");
            try {
                client.chooseTrainCardFromFaceUpCards(Model.getInstance().getCurrentGame().getID(), Model.getInstance().getCurrentUser(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            delay();
            view.sendMessage("Updating number of invisible train cards in deck");
            try {
                client.chooseTrainCardFromDeck(Model.getInstance().getCurrentGame().getID(), Model.getInstance().getCurrentUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
            delay();
            view.sendMessage("Updating number of destination cards in deck");
            try {
                client.sendDestinationCards(Model.getInstance().getCurrentGame().getID(), Model.getInstance().getCurrentUser(), Model.getInstance().getCurrentGame().getTopOfDestinationCardDeck(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            delay();
            view.sendMessage("Adding a claimed route: Calgary-Winnipeg");
            client.testHaveOpponentClaimRoute(new Route(City.CALGARY, City.WINNIPEG, 6, TrainCardType.PASSENGER));
            delay();
            view.sendMessage("Adding a chat message");
            try {
                client.sendMessage(client.getCurrentGame().getID(), client.getCurrentUser(), "Hey Guys!");
            } catch (Exception e) {
                view.sendMessage("Chat failed to send");
            }
            delay();
            view.sendMessage("Advancing turn to next player");
            client.finishTurn();

            return true;
        }

        private void delay() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void runPhase2Test() {
        runPhase2TestTask task = new runPhase2TestTask();
        task.execute();

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
