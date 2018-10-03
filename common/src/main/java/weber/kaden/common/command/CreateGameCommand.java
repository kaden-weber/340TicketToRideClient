package weber.kaden.common.command;


import weber.kaden.common.Results;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    CreateGameCommand(List<String> params) {
        this.playerID = params.get(0);
        if (params.size() > 1) {
            this.gameID = params.get(1);
        } else {
            this.gameID = UUID.randomUUID().toString();
        }
    }

    @Override
    public Results execute() {
        Player firstPlayer = Model.getInstance().getPlayer(playerID);
        List<Player> players = new ArrayList<Player>();
        players.add(firstPlayer);
        Game game = new Game(players, gameID);
        Results toReturn = null;
        if (Model.getInstance().addGame(game)) {
            toReturn = new Results(game, true, null);
        } else {
            toReturn = new Results(null, false, null);
        }
        return toReturn;
    }
}
