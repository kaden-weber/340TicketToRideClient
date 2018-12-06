package weber.kaden.common.command.CommandClasses;



import java.util.List;

import weber.kaden.common.results.GameResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class JoinGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    public JoinGameCommand(List<String> params) {
        this.playerID = params.get(0);
        this.gameID = params.get(1);
    }

    @Override
    public Results execute() {
        Model model = Model.getInstance();
        Player player = model.getPlayer(playerID);
        Game game = model.getGame(gameID);
        if (model.addPlayerToGame(player, game)) {
            return new GameResults(model.getGame(game.getID()), true, null);
        } else {
            return new GameResults(null, false, "cannot add player to game");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
