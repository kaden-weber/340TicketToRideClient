package weber.kaden.myapplication.ui;

import java.util.Observable;
import java.util.Observer;

import weber.kaden.myapplication.model.ClientFacade;

public class LoginPresenter implements Observer {

    private LoginActivity activity;
    private ClientFacade client;

    public LoginPresenter(LoginActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

    public boolean login(String username, String password) throws Exception {
        return client.login(username, password);
    }

    public void register(String username, String password, String confirmPassword) throws Exception {
        client.register(username, password);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
