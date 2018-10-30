package weber.kaden.server;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.iCommandManager;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

public class CommandManager implements iCommandManager {

    private static CommandManager sCommandManager;
    public static CommandManager getInstance(){
        if (sCommandManager == null) {
            sCommandManager = new CommandManager();
            CommandFactory.setCommandManager(sCommandManager);
        }
        return sCommandManager;
    }

    private List<CommandData> mCommandDataList;

    private CommandManager(){
        mCommandDataList = new ArrayList<>();
    }

    Results processCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getInstance().getCommand(commandData);
        if (command.hasID()) {
            mCommandDataList.add(commandData);
        }
        return command.execute();
    }

    @Override
    public List<Game> gamesList() {
        return Model.getInstance().getGames();
    }

    @Override
    public Game getGameByID(String gameID) {
        return Model.getInstance().getGame(gameID);
    }

    @Override
    public List<CommandData> getLatestCommands(String gameID, String lastID) {
        List<CommandData> toReturn = new ArrayList<CommandData>();
        int index = 0;
        for (int i = 0; i < mCommandDataList.size(); i++) {
            if (this.mCommandDataList.get(i).getCommandID().equals(lastID)) {
                index = i;
                break;
            }
        }
        for (int i = index + 1; i < mCommandDataList.size(); i++) {
            toReturn.add(mCommandDataList.get(i));
        }
        return toReturn;
    }
}
