package weber.kaden.ticketToRide.ui.turnmenu;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Player;
import weber.kaden.ticketToRide.model.ClientFacade;

public class SeeOtherPlayersPresenter implements Observer {

    private SeeOtherPlayersFragment seeOtherPlayersFragment;
    private ClientFacade client;

    public SeeOtherPlayersPresenter(SeeOtherPlayersFragment seeOtherPlayersFragment, ClientFacade client) {
        this.seeOtherPlayersFragment = seeOtherPlayersFragment;
        this.client = client;
    }

    public List<Player> getPlayers() {
        List<Player> toreturn = client.getCurrentGame().getPlayers();
        /*for (int i = 0; i < toreturn.size(); i++) {
            if (toreturn.get(i).getID().equals(client.getCurrentUser())) {
                toreturn.remove(i);
                i--;
            }
        }*/
        return toreturn;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
