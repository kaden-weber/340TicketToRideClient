package weber.kaden.common.model;

public class ChatMessage {
    private String playerID;
    private String chatMessage;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatMessage(String playerID, String chatMessage) {
        this.playerID = playerID;
        this.chatMessage = chatMessage;
    }

    @Override
    public String toString() {
        return playerID + ": " + chatMessage;
    }
}
