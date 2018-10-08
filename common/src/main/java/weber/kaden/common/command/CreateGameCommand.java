package weber.kaden.common.command;


import weber.kaden.common.GameResults;
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
    private String ID = null;
    private String gameName;

    public CreateGameCommand(List<String> params, String ID) {
        this.playerID = params.get(0);
        this.gameName = params.get(1);
        if (params.size() > 2) {
            this.gameID = params.get(2);
        } else {
            this.gameID = UUID.randomUUID().toString();
        }
        this.ID = ID;
    }

    @Override
    public Results execute() {
        Player firstPlayer = Model.getInstance().getPlayer(playerID);
        List<Player> players = new ArrayList<Player>();
        players.add(firstPlayer);
        Game game = new Game(players, gameID, gameName);
        Results toReturn = null;
        if (Model.getInstance().addGame(game)) {
            toReturn = new GameResults(game, true, null);
        } else {
            toReturn = new GameResults(null, false, "unable to add game to model");
        }
        return toReturn;
    }


}
