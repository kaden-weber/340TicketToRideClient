package weber.kaden.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, List<CommandData>> mCommandDataList;

    private CommandManager(){
        mCommandDataList = new HashMap<>();
    }

    Results processCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getInstance().getCommand(commandData);
        if (command.hasID()) {
            if (mCommandDataList.get(commandData.getParams().get(0)) == null) {
                mCommandDataList.put(commandData.getParams().get(0), new ArrayList<CommandData>());
            }
            mCommandDataList.get(commandData.getParams().get(0)).add(commandData);
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
        boolean get = false;
        for (int i = 0; i < mCommandDataList.get(gameID).size(); i++) {
            if (get) {
                toReturn.add(this.mCommandDataList.get(gameID).get(i));
            }
            if (this.mCommandDataList.get(gameID).get(i).getCommandID().equals(lastID)) {
                get = true;
            }
        }
        return toReturn;
    }
}
