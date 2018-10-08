package weber.kaden.myapplication.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Model;
import weber.kaden.myapplication.serverProxy.ServerProxy;
import weber.kaden.myapplication.ui.LoginPresenter;

public class ClientFacade {

	private LoginPresenter presenter;

    public boolean login(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.LOGIN);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Login resulted in null");
        }
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

	    Model.getInstance().setCurrentUser(username);

        return true;
    }

    public boolean register(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.REGISTER);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Register resulted in null");
        }
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

        Model.getInstance().setCurrentUser(username);

        return true;
    }

    public boolean executeLocalCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getCommand(commandData);
        Results results = command.execute();
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
	    }

	    return true;
    }
}