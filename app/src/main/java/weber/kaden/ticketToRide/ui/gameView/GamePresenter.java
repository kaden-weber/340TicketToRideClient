package weber.kaden.ticketToRide.ui.gameView;

import java.util.ArrayList;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.model.ClientFacade;

/**
 * Uses the GameViewInterface to interact with a modular UI that displays the game map.
 * In this case, many of the actions that would be called in this presenter were instead made a part of
 * presenters that correspond to small pieces of the game.
 *
 */
public class GamePresenter implements Observer {
    private GameViewInterface view;
    private ClientFacade client;

    /**
     * Class constructor that uses dependency injection
     * @param view The interface to a game view
     * @param client A facade used to interact with the client layer
     * Also adds itself as an observer to the model
     */
    public GamePresenter(GameViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    /**
     *
     * This uses the clientFacade to check with the local model to see if a player has enough cards
     * to claim a route.
     * @param size The size of the route, the number of cards necessary to claim it, must be between
     *             0 and 6.
     * @param type The type of route, uses locomotive to specify a gray route. Must be a valid train
     *             card type.
     * @return Returns true if the user has enough cards of a certain type (enough to claim a route)
     */
    boolean routeClaimable(int size, TrainCardType type){
        return client.playerCanClaimRoute(size, type);
    }

    /**
     * Updates the UI with all of the data that could have changed when the model updated
     * @param observable The observed model object
     * @param arg The game object that the observable uses to wrap all of the data that may have
     *           changed, must be a non null Game object.
     */
    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof Game) {
            Game game = (Game) arg;
            Player player = game.getPlayer(client.getCurrentUserID());
            List<DestinationCard> destCards = player.getDestinationCardHand();
            List<TrainCard> trainCards = player.getTrainCards();
            List<Integer> points = new ArrayList<>();
            points.add(player.getScore());
            view.setNewValues(destCards, trainCards, points);
            updateClaimedRoutes(game);
        }
    }

    /**
     *
     * @param game Called whenever the number of claimed routes changes, gives the view a list of
     *             claimed routes for every player.
     */
    private void updateClaimedRoutes(Game game){
        for( Player player : game.getPlayers()){
            //if this player claimed the route, or there are only 2 players, disable second route
            boolean disableSecond = false;
            if(player.getID().equals(client.getCurrentUserID()) || client.getNumberOfPlayersInGame() <= 2 ){
                disableSecond = true;
            }

            view.updateClaimedRoutes(player.getColor(),
                    game.RoutesClaimedByPlayer(player.getID()), disableSecond);
        }
    }

}
