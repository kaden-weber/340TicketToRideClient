package weber.kaden.ticketToRide.ui.chat;

import java.util.List;

import weber.kaden.common.model.ChatMessage;

public interface ChatViewInterface {
    void sendChat(String message);
    void printChatError(String message);
    void updateChat(List<ChatMessage> messages);
}
