package weber.kaden.ticketToRide.ui.gameOver;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.model.Model;
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

	public void quitGame(String username, String gameName) throws Exception {
		client.exitLobby(username, gameName);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
