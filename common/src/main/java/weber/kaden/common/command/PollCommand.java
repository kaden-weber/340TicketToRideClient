package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;

public class PollCommand implements Command {

    private String username;
    private String pollType;
    private String info;
    private iCommandManager commandManager;

    public PollCommand(List<String> params, iCommandManager commandManager) {
        this.username = username;
        this.pollType = pollType;
        this.info = info;
        this.commandManager = commandManager;
    }

    @Override
    public Results execute() {
        if (pollType.equals("gamesList")) {
            return commandManager.gamesList();
        } else if (pollType.equals("gameLobby")) {
            return commandManager.gameLobby();
        } else if (pollType.equals("game")) {
            return commandManager.game();
        } else {
            return new Results(null, false, "incorrect polltype, please use: 'gamesList', 'gameLobby', or 'game'");
        }
    }
}
