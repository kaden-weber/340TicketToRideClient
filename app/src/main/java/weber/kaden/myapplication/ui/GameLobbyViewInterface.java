package weber.kaden.myapplication.ui;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Player;

public interface GameLobbyViewInterface {
    void startGame();
    void updatePlayersList(List<Player> players);
}
