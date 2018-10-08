package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.model.Game;

public class PollGameCommand implements Command{
	private String playerID;
	private String gameID;
	private iCommandManager commandManager;

	public PollGameCommand(List<String> params, iCommandManager commandManager) {
		this.playerID = params.get(0);
		this.gameID = params.get(1);
		this.commandManager = commandManager;
	}

	@Override
	public Results execute() {
		Game game = commandManager.currentGame(this.gameID);
		if (game == null) {
			return new Results(null, false, "Game not fetched");
		}
		else {
			return new Results(game, true, "");
		}
	}
}
