package weber.kaden.server;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;

public class CommandManager {

    private static CommandManager sCommandManager;
    public static CommandManager getInstance(){
        if (sCommandManager == null) {
            sCommandManager = new CommandManager();
        }
        return sCommandManager;
    }

    private List<CommandData> mCommandDataList;

    private CommandManager(){
        mCommandDataList = new ArrayList<>();
    }

    Results processCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getCommand(commandData);
        mCommandDataList.add(commandData);
        return command.execute();
    }



}
