package weber.kaden.common.command;



import java.util.List;

import weber.kaden.common.Results;

public class RegisterCommand implements Command {

    private String username = null;
    private String password = null;

    public RegisterCommand(List<String> params) {
        this.username = params.get(0);
        this.password = params.get(1);
    }

    @Override
    public Results execute() {
        return null;
    }
}
