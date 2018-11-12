package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.model.Route;

public class CommandDataClaimRoute extends CommandData {
    private Route route;

    public CommandDataClaimRoute(List<String> params, CommandType type, Route route) {
        super(params, type);
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
