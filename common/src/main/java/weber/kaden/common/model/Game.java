package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.print.attribute.standard.NumberUp;

import weber.kaden.common.GameResults;
import weber.kaden.common.Results;

public class Game {
    private List<Player> players;
    private String ID;
    private String gameName;
    private boolean started;
    private List<ChatMessage> chat;
    private List<DestinationCard> destinationCardDeck;
    private List<DestinationCard> destinationCardDiscard;
    private List<TrainCard> trainCardDeck;
    private List<TrainCard> trainCardDiscard;

    public Game() {
        this.players = new ArrayList<Player>();
        this.ID = UUID.randomUUID().toString();
        this.started = false;
    }

    public Game(List<Player> players, String ID, String gameName) {
        this.players = players;
        this.ID = ID;
        this.gameName = gameName;
        this.started = false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<ChatMessage> getChat() {
        return chat;
    }

    public void setChat(List<ChatMessage> chat) {
        this.chat = chat;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean addPlayer(Player player) {
        return this.players.contains(player) || this.players.add(player);
    }

    public boolean removePlayer(Player player) {
        return this.players.remove(player);
    }

    public boolean removePlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                this.players.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    public boolean hasPlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                return this.players.get(i);
            }
        }
        return null;
    }

    public boolean addChat(ChatMessage chat) {
        return this.chat.add(chat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return started == game.started &&
                Objects.equals(players, game.players) &&
                Objects.equals(ID, game.ID) &&
                Objects.equals(gameName, game.gameName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(players, ID, gameName, started);
    }

    public boolean start() {
        // TESTING ONLY, CHANGE 1 to 2
        if (this.getPlayers().size() < 1 || this.getPlayers().size() > 5) {
            return false;
        }
        InitalizeDecks();
        DealTrainCardsToPlayers();
        DealDestinationCardsToPlayers();
        setStarted(true);
        return true;
    }

    private void InitalizeDecks() {
        this.destinationCardDeck = InitialDecks.getDestinationCards();
        this.destinationCardDiscard = new ArrayList<DestinationCard>();

        this.trainCardDeck = InitialDecks.getTrainCards();
        this.trainCardDiscard = new ArrayList<TrainCard>();
    }

    private void DealDestinationCardsToPlayers() {
        for (int i = 0; i < this.players.size(); i++) {
            DealDestinationCardsToPlayer(this.players.get(i));
        }
    }

    private void DealTrainCardsToPlayers() {
        for (int i = 0; i < this.players.size(); i++) {
            List<TrainCard> cards = new ArrayList<TrainCard>();
            for (int t = 0; t < 4; t++) {
                cards.add(this.trainCardDeck.get(0));
                this.trainCardDeck.remove(0);
            }
            this.players.get(i).DealTrainCards(cards);
        }
    }

    private void DealDestinationCardsToPlayer (Player player) {
        int NumCards = 3;
        if (this.destinationCardDeck.size() < NumCards) {
            NumCards = this.destinationCardDeck.size();
        }
        List<DestinationCard> cards = new ArrayList<DestinationCard>();
        for (int i = 0; i < NumCards; i++) {
            cards.add(this.destinationCardDeck.get(i));
        }
        for (int i = 0; i < NumCards; i++) {
            this.destinationCardDeck.remove(0);
        }
        player.DealDestinationCards(cards);
    }

    public boolean PlayerDrawDestinationCards(String playerID, List<DestinationCard> cards) {
        return this.getPlayer(playerID).DrawDestinationCards(cards);
    }

    public boolean DiscardDestionationCards(List<DestinationCard> cards) {
        return this.destinationCardDiscard.addAll(cards);
    }
}
