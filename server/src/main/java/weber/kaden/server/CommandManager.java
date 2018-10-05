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

    @Override
    public Results gamesList() {
        List<Game> games = Model.getInstance().getGames();
        return new Results(games, true, null);
    }

    @Override
    public Results gameLobby() {

        return null;
    }

    @Override
    public Results game() {
        //Game game = Model.getInstance().getGame()
        return null;
    }

// return games list and current game


}
