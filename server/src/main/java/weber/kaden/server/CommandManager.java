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

    /**
     * class invariants:
     * 1. There is only ever one CommandManager (it is singleton)
     * 2. The command manager is injected into the command factory
     * 3. mCommandDataList is never null
     * */

    /**
     * @return CommandManager
     * injects commandManager dependency into CommandFactory
     * @post returns singleton of CommandManager
     * */
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

    /**
     * @param commandData CommandData holding the command to execute and the data for the command
     * @return Results the results of the command execution
     * @throws Exception throws an exception if the command cannot be built
     * @pre model is in a state that can handle the command
     * @pre commandData has all the required data to build the command
     * @post successful command execution
     * @post command is stored in game history if the command is tied to a game
     * */
    public Results processCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getInstance().getCommand(commandData);
        if (command.hasID()) {
            if (mCommandDataList.get(commandData.getParams().get(0)) == null) {
                mCommandDataList.put(commandData.getParams().get(0), new ArrayList<CommandData>());
            }
            mCommandDataList.get(commandData.getParams().get(0)).add(commandData);
        }
        return command.execute();
    }

    /**
     * @return List<Game> the list of current games in the model
     * @pre none
     * @post the current list of games is unedited
     * @post the current list of games is returned
     * */
    @Override
    public List<Game> gamesList() {
        return Model.getInstance().getGames();
    }

    /**
     * @param gameID the gameID to be searched for
     * @pre gameID is a valid String gameID
     * @post returns the game corresponding to the given ID.
     * @return Game the game with the gameID specified in the parameter
     * */
    @Override
    public Game getGameByID(String gameID) {
        return Model.getInstance().getGame(gameID);
    }

    /**
     * @param gameID a String gameID
     * @pre gameID corresponds to a game that exists
     * @post model state does not change
     * @post returned list is comprehensive of commands issued in the game
     * @return List<CommandData> the list of CommandData that have created commands to be executed on the game
     * */
    @Override
    public List<CommandData> getLatestCommands(String gameID) {
        List<CommandData> toReturn = new ArrayList<CommandData>();
        for (int i = 0; i < mCommandDataList.get(gameID).size(); i++) {
            toReturn.add(this.mCommandDataList.get(gameID).get(i));
        }
        return toReturn;
    }
}
