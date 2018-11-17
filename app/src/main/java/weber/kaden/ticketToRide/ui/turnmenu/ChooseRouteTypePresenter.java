package weber.kaden.ticketToRide.ui.turnmenu;

import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseRouteTypePresenter {
    private ChooseRouteTypeFragment view;
    private ClientFacade client;

    public ChooseRouteTypePresenter(ChooseRouteTypeFragment view, ClientFacade client){
        this.view = view;
        this.client = client;
    }


}
