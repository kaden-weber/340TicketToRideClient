package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class RegisterCommand implements Command {

    private String username = null;
    private String password = null;

    RegisterCommand(List<String> params) {
        this.username = params.get(0);
        this.password = params.get(1);
    }

    @Override
    public Results execute() {
        if(Model.getInstance().addPlayer(new Player(username, password))){
            return new Results(null, true, null);
        } else {
            // At this point if the player can't be added, the username must already be taken
            return new Results(null, false, "Username taken");
        }
    }
}
