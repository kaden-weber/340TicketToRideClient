package weber.kaden.myapplication.ui.turnmenu;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.myapplication.model.ClientFacade;

public class ChooseDestinationCardsPresenter implements Observer {
    private ChooseDestinationCardsFragment fragment;
    private ClientFacade client;

    public ChooseDestinationCardsPresenter(ChooseDestinationCardsFragment fragment, ClientFacade client) {
        this.fragment = fragment;
        this.client = client;
    }

    public List<DestinationCard> getDrawableDestinationCards() {
        return client.getDestinationCardsForTurn();
    }

    public void chooseDestinationCards(String username, String gameId, List<DestinationCard> drawnCards, List<DestinationCard> discardedCards) throws Exception{
        client.sendDestinationCards(username, gameId, drawnCards, discardedCards);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
