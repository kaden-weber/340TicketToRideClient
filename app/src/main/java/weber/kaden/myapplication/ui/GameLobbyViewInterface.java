package weber.kaden.myapplication.ui;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Player;

public interface GameLobbyViewInterface {
    void setupGame();
    void updatePlayersList(List<Player> players);
}
