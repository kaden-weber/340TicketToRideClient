package weber.kaden.myapplication.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;
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
        //Idk what to do here
    }
}
