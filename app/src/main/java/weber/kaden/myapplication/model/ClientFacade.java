package weber.kaden.myapplication.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.serverProxy.ServerProxy;
import weber.kaden.myapplication.ui.LoginPresenter;

public class ClientFacade {

    private Model model;
    private LoginPresenter presenter;

    public boolean login(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.LOGIN);

        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();

        ServerProxy.getInstance().sendCommand(commandData);

        return true;
    }
    public boolean register(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.REGISTER);

        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();

        ServerProxy.getInstance().sendCommand(commandData);

        return true;
    }

}
