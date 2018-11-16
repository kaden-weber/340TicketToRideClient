package weber.kaden.ticketToRide.ui;

import weber.kaden.common.model.City;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ClaimRoutePresenter {
    private ClaimRouteFragment view;
    private ClientFacade client;
    private Model model;

    public ClaimRoutePresenter(ClaimRouteFragment view, ClientFacade client) {
        this.view = view;
        this.client = client;
        this.model = Model.getInstance();
    }

    public void claimRoute(String city1, String city2, TrainCardType type, Integer cost, boolean isSecondRoute) {
        try {
            City start = City.valueOf(toEnumValue(city1));
            City end = City.valueOf(toEnumValue(city2));
            Route route = new Route(start, end, cost, type, isSecondRoute);
            String gameId = Model.getInstance().getCurrentGame().getID();
            String userId = Model.getInstance().getCurrentUser();
            client.claimRoute(gameId, userId, route, type);
        } catch (Exception e) {
            view.printError(e.getMessage());
        }

    }

    private String toEnumValue(String str) {
        str = str.replaceAll(" ", "_").toUpperCase();
        return str;
    }
}
