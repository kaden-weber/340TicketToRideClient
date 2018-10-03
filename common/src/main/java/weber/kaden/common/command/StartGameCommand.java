package weber.kaden.common.command;



import java.util.List;

import weber.kaden.common.Results;

public class StartGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    StartGameCommand(List<String> params) {
        this.playerID = params.get(0);
        this.gameID = params.get(1);
    }

    @Override
    public Results execute() {
        return null;
    }
}
