package weber.kaden.server;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.injectedInterfaces.iCommandManager;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

import static weber.kaden.common.command.CommandType.POLLCOMMANDS;
import static weber.kaden.common.command.CommandType.POLLGAME;
import static weber.kaden.common.command.CommandType.POLLGAMESLIST;

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

    private CommandManager(){}

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
            Model.getInstance().addGameHistoryToGame(commandData.getParams().get(0), commandData);
        }
        Results results = command.execute();
        if (results.success() && !(commandData.getType().equals(POLLGAMESLIST) || commandData.getType().equals(POLLGAME) || commandData.getType().equals(POLLCOMMANDS))) {
            PersistenceManager.getInstance().update(commandData);
        }
        return results;
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
    @Deprecated
    public List<CommandData> getLatestCommands(String gameID) {
        List<CommandData> toReturn = new ArrayList<CommandData>();
        return toReturn;
    }
}
