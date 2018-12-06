package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.results.ListResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.injectedInterfaces.iCommandManager;
import weber.kaden.common.model.Game;

public class PollGamesListCommand implements Command {

    private String username;
    private iCommandManager commandManager;

    public PollGamesListCommand(List<String> params, iCommandManager commandManager) {
        this.username = params.get(0);
        this.commandManager = commandManager;
    }

    @Override
    public Results execute() {
        List<Game> games = commandManager.gamesList();
        if (games == null) {
            return new ListResults(null, false, "incorrect polltype, please use: 'gamesList' or 'getGameByID'");
        } else {
        	return new ListResults(games, true, "");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
