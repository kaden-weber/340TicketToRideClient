package weber.kaden.ticketToRide.ui.turnmenu;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseTrainCardsPresenter implements Observer {
    private ChooseTrainCardsFragment fragment;
    private ClientFacade client;

    public ChooseTrainCardsPresenter(ChooseTrainCardsFragment fragment, ClientFacade client) {
        this.fragment = fragment;
        this.client = client;
        Model.getInstance().addObserver(this);
    }

    public void removeAsObserver() {
    	Model.getInstance().deleteObserver(this);
    }

    public List<TrainCard> getFaceUpTrainCards() {
        return Model.getInstance().getCurrentGame().getFaceUpTrainCardDeck();
    }

    public void drawFaceUpTrainCard(String gameId, String username, int drawnCardIndex) throws Exception {
        client.chooseTrainCardFromFaceUpCards(gameId, username, drawnCardIndex);
    }

    public void drawTrainCardFromDeck(String gameId, String username) throws Exception {
        client.chooseTrainCardFromDeck(gameId, username);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Game) {
            Game game = (Game) o;
            fragment.updateCards(game.getFaceUpTrainCardDeck());
        }
    }
}
