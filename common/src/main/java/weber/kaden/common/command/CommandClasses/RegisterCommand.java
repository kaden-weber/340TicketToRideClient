package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.results.GenericResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class RegisterCommand implements Command {

    private String username = null;
    private String password = null;

    public RegisterCommand(List<String> params) {
        this.username = params.get(0);
        this.password = params.get(1);
    }

    @Override
    public Results execute() {
        Player test = new Player(username, password);
        if(Model.getInstance().addPlayer(test)){
            return new GenericResults(null, true, null);
        } else {
            // At this point if the player can't be added, the username must already be taken
            return new GenericResults(null, false, "Username taken");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
