package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.ChatMessage;
import weber.kaden.common.model.Model;

public class ChatCommand implements Command {
    private String gameID;
    private String playerID;
    private String chatMessage;

    public ChatCommand(List<String> params) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.chatMessage = params.get(2);
    }

    @Override
    public Results execute() {
        ChatMessage chat = new ChatMessage(this.playerID, this.chatMessage);
        if (Model.getInstance().getGame(gameID).addChat(chat)) {
            return new GenericResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "Chat not added");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
