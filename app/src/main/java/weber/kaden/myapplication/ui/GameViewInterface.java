package weber.kaden.myapplication.ui;

import java.util.List;

import weber.kaden.common.model.PlayerColors;
import weber.kaden.common.model.Route;

public interface GameViewInterface {

    void sendMessage(String message);

    void updateClaimedRoutes(PlayerColors color, List<Route> routes);
}
