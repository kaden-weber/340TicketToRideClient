package weber.kaden.common.command;



import java.util.List;

import javax.jws.WebParam;

import weber.kaden.common.Results;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class JoinGameCommand implements Command {

    private String playerID = null;
    private String gameID = null;
    private String commandID = null;

    public JoinGameCommand(List<String> params, String commandID) {
        this.playerID = params.get(0);
        this.gameID = params.get(1);
        this.commandID = commandID;
    }

    @Override
    public Results execute() {
        Model model = Model.getInstance();
        Player player = model.getPlayer(playerID);
        Game game = model.getGame(gameID);
        if (model.addPlayerToGame(player, game)) {
            return new Results(model.getGame(game.getID()), true, null);
        } else {
            return new Results(null, false, "cannot add player to game");
        }
    }
}
