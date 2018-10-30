package weber.kaden.myapplication.ui;

import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.model.ClientFacade;

public class GamePresenter {
    private GameViewInterface view;
    private ClientFacade client;

    public GamePresenter(GameViewInterface view, ClientFacade client) {
        this.view = view;
        this.client = client;
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

    	return null;
    }

    public boolean chooseDestinationCards(List<DestinationCard> drawnCards) {

    	return false;
    }
}
