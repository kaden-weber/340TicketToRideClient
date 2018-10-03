package weber.kaden.myapplication.model;

import weber.kaden.common.model.Model;
import weber.kaden.myapplication.serverProxy.ServerProxy;
import weber.kaden.myapplication.ui.LoginPresenter;

public class ClientFacade {

    private Model model;
    private LoginPresenter presenter;

    public boolean login(String username, String password) throws Exception {
        ServerProxy.getInstance().login(username, password);

        return false;
    }
    public boolean register(String username, String password) throws Exception {
        ServerProxy.getInstance().register(username, password);

        return false;
    }

}
