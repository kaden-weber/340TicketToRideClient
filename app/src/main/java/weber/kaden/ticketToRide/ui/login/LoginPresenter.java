package weber.kaden.ticketToRide.ui.login;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.ticketToRide.model.ClientFacade;

public class LoginPresenter implements Observer {

    private LoginViewInterface activity;
    private ClientFacade client;

    public LoginPresenter(LoginActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

    public boolean login(String username, String password, String serverIP, String serverPort) throws Exception {
       client.login(username,password, serverIP, serverPort);

        return true;
    }

    public void register(String username, String password, String confirmPassword, String serverIP,
                         String serverPort) throws Exception {
        if (!password.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        } else {
            client.register(username,password, serverIP, serverPort);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
