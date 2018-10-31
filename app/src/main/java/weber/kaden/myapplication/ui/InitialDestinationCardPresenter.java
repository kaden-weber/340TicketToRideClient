package weber.kaden.myapplication.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.model.ClientFacade;

public class InitialDestinationCardPresenter implements Observer {
    private ChooseInitialDestinationFragment activity;
    private ClientFacade client;

    public InitialDestinationCardPresenter(ChooseInitialDestinationFragment activity, ClientFacade client){
        Model.getInstance().addObserver(this);
        this.activity = activity;
        this.client = client;
    }
    public List<DestinationCard> sendCards(String playerId, String gameId, List<DestinationCard> kept, List<DestinationCard> discarded) throws Exception {
        return client.sendDestinationCards(playerId, gameId, kept, discarded);
    }

    @Override
    public void update(Observable o, Object arg){

    }
}
