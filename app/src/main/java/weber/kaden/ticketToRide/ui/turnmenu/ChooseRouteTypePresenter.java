package weber.kaden.ticketToRide.ui.turnmenu;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseRouteTypePresenter {
    private ChooseRouteTypeFragment view;
    private ClientFacade client;

    public ChooseRouteTypePresenter(ChooseRouteTypeFragment view, ClientFacade client){
        this.view = view;
        this.client = client;
    }


    public List<TrainCardType> getValidTypes(Integer cost) {
        List<TrainCardType> types = new ArrayList<>();
        // add a type for each valid route of the player...
        if(client.playerCanClaimRoute(cost, TrainCardType.HOPPER)){
            types.add(TrainCardType.HOPPER);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.PASSENGER)){
            types.add(TrainCardType.PASSENGER);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.TANKER)){
            types.add(TrainCardType.TANKER);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.CABOOSE)){
            types.add(TrainCardType.CABOOSE);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.COAL)){
            types.add(TrainCardType.COAL);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.BOX)){
            types.add(TrainCardType.BOX);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.FREIGHT)){
            types.add(TrainCardType.FREIGHT);
        }
        if(client.playerCanClaimRoute(cost, TrainCardType.REEFER)){
            types.add(TrainCardType.REEFER);
        }
        //what about claiming a route with just locomotives? Does this work?
        if(client.playerCanClaimRoute(cost, TrainCardType.LOCOMOTIVE)){
            types.add(TrainCardType.LOCOMOTIVE);
        }
        return types;
    }
}
