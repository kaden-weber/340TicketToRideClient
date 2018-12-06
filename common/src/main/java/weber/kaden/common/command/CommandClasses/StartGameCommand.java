package weber.kaden.common.command.CommandClasses;



import java.util.List;

import weber.kaden.common.results.GameResults;
import weber.kaden.common.results.GenericResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

@Deprecated
public class StartGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    public StartGameCommand(List<String> params) {
        this.playerID = params.get(0);
        this.gameID = params.get(1);
    }

    @Override
    public Results execute() {
        Game game = Model.getInstance().startGame(gameID);
        if (game == null) {
            return new GenericResults (null, false, "game not started");
        } else {
            return new GameResults(game, true, "");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
