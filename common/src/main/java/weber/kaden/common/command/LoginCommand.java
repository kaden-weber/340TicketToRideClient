package weber.kaden.common.command;



import java.util.List;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class LoginCommand implements Command {

    private String username = null;
    private String password = null;

    public LoginCommand(List<String> params) {
        this.username = params.get(0);
        this.password = params.get(1);
    }

    @Override
    public Results execute() {
        Player player = Model.getInstance().getPlayer(username);
        if (player != null) {
            if (player.getPassword().equals(password)) {
                return new GenericResults(null, true, null);
            } else {
                return new GenericResults(null, false, "Incorrect password");
            }
        } else {
            return new GenericResults(null, false, "Username does not exist");
        }
    }
}
