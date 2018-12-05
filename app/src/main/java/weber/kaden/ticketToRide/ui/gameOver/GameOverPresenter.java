package weber.kaden.ticketToRide.ui.gameOver;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.ticketToRide.model.ClientFacade;

public class GameOverPresenter implements Observer {
	private GameOverViewInterface view;
	private ClientFacade client;
	private Model model = Model.getInstance();

	GameOverPresenter(GameOverViewInterface view, ClientFacade client) {
		this.view = view;
		this.client = client;
		model.addObserver(this);
	}

	public List<Player> getPlayers() {
		return client.getCurrentGame().getPlayers();
	}

	public void quitGame(String username, String gameName) throws Exception {
		client.exitLobby(username, gameName);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
