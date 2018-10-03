package weber.kaden.myapplication.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.serverProxy.ServerProxy;

public class LoginPresenter implements Observer {

    private LoginActivity activity;
    private ClientFacade client;

    public LoginPresenter(LoginActivity activity, ClientFacade client) {
        this.activity = activity;
        this.client = client;
    }

    public boolean login(String username, String password) throws Exception {
       client.login(username,password);

        return true;
    }

    public void register(String username, String password, String confirmPassword) throws Exception {
        if (!password.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        } else {
            client.register(username,password);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
