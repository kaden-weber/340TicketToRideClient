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
        return client.login(username, password); // throws exception if credentials are invalid
    }

    public void register(String username, String password) throws Exception {
        /*
         receive credentials, pass them to the facade if valid.
         if facade tells me to destroy, do I destroy? or do I tell view to destroy itself?
         if facade tells me to throw an exception, tell view to display an error.
         */

        //client.register(username, password); // throws exception if user already exists
        // either destroy the view or tell the view to destroy itself
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
