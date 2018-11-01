package weber.kaden.myapplication.ui.turnmenu;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.model.ClientFacade;

public class ChooseTrainCardsPresenter implements Observer {
    private ChooseTrainCardsFragment fragment;
    private ClientFacade client;

    public ChooseTrainCardsPresenter(ChooseTrainCardsFragment fragment, ClientFacade client) {
        this.fragment = fragment;
        this.client = client;
        Model.getInstance().addObserver(this);
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

    }
}
