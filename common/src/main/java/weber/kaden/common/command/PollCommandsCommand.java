package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.CommandListResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;

public class PollCommandsCommand implements Command {
    private String gameID;
    private iCommandManager commandManager;

    public PollCommandsCommand(List<String> params, iCommandManager commandManager) {
        this.gameID = params.get(0);
        this.commandManager = commandManager;
    }

    @Override
    public Results execute() {
        List<CommandData> commandData = this.commandManager.getLatestCommands(this.gameID);
        if (commandData == null) {
            return new GenericResults(null, false, "Command list not fetched");
        }
        else {
            return new CommandListResults(commandData, true, "");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
