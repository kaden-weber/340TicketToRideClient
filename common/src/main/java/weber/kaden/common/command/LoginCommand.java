package weber.kaden.common.command;



import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;

public class LoginCommand implements Command {

    private String username = null;
    private String password = null;

    LoginCommand(List<String> params) {
        this.username = params.get(0);
        this.password = params.get(1);
    }

    @Override
    public Results execute() {
        if (Model.getInstance().hasPlayer(new Player(username, password))) {
            return new Results(null, true, null);
        } else {
            return new Results(null, false, null);
        }
    }
}
