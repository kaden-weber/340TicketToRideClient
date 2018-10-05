package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;

public class PollCommand implements Command {

    private String username;
    private String pollType;
    private String info;
    private iCommandManager commandManager;

    public PollCommand(List<String> params, iCommandManager commandManager) {
        this.username = params.get(0);
        this.pollType = params.get(1);
        this.info = params.get(2);
        this.commandManager = commandManager;
    }

    @Override
    public Results execute() {
        if (pollType.equals("gamesList")) {
            return commandManager.gamesList();
        } else if (pollType.equals("currentGame")) {
            return commandManager.currentGame(info);
        } else {
            return new Results(null, false, "incorrect polltype, please use: 'gamesList' or 'currentGame'");
        }
    }
}
