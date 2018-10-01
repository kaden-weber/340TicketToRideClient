package weber.kaden.common.command;


import weber.kaden.common.Results;

import java.util.List;
import java.util.UUID;

public class CreateGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;

    public CreateGameCommand(List<String> params) {
        this.playerID = params.get(0);
        if (params.size() > 1) {
            this.gameID = params.get(1);
        } else {
            this.gameID = UUID.randomUUID().toString();
        }
    }

    @Override
    public Results execute() {
        return null;
    }
}
