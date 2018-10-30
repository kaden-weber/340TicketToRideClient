package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

public class SetUpGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    public SetUpGameCommand(List<String> params) {
        this.playerID = params.get(0);
        this.gameID = params.get(1);
    }

    @Override
    public Results execute() {
        Game game = Model.getInstance().setUpGame(gameID);
        if (game == null) {
            return new GenericResults(null, false, "game not started");
        } else {
            return new GameResults(game, true, "");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
