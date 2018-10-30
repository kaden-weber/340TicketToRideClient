package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Game {
    private List<Player> players;
    private String ID;
    private String gameName;
    private boolean started;
    private boolean setup;
    private List<ChatMessage> chat;
    private List<DestinationCard> destinationCardDeck;
    private List<DestinationCard> destinationCardDiscard;
    private List<TrainCard> trainCardDeck;
    private List<TrainCard> trainCardDiscard;
    private List<TrainCard> faceupTrainCardDeck;
    private List<Route> claimedRoutes;
    private List<Route> unclaimedRoute;
    private int currentPlayer;

    public Game() {
        this.players = new ArrayList<Player>();
        this.ID = UUID.randomUUID().toString();
        this.started = false;
        this.setup = false;
    }

    public Game(List<Player> players, String ID, String gameName) {
        this.players = players;
        this.ID = ID;
        this.gameName = gameName;
        this.started = false;
        this.setup = false;
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

    public boolean isInStartUp() {
        return started;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
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

    public boolean setUp() {
        // TESTING ONLY, CHANGE 1 to 2
        if (this.getPlayers().size() < 1 || this.getPlayers().size() > 5) {
            return false;
        }
        InitalizeDecks();
        AssignColors();
        setSetup(true);
        return true;
    }

    public boolean start() {
        DealTrainCardsToPlayers();
        DealDestinationCardsToPlayers();
        setFirstPlayer();
        setStarted(true);
        return true;
    }

    private void AssignColors() {
        this.players.get(0).setColor(PlayerColors.BLACK);
        if (this.players.size() > 1) {
            this.players.get(1).setColor(PlayerColors.BLUE);
            if (this.players.size() > 2) {
                this.players.get(2).setColor(PlayerColors.GREEN);
                if (this.players.size() > 3) {
                    this.players.get(3).setColor(PlayerColors.RED);
                    if (this.players.size() > 4) {
                        this.players.get(4).setColor(PlayerColors.YELLOW);
                    }
                }
            }
        }
    }

    private void InitalizeDecks() {
        this.destinationCardDeck = new ArrayList<DestinationCard>(InitialGameSetUpVariables.getDestinationCards());
        this.destinationCardDiscard = new ArrayList<DestinationCard>();

        this.trainCardDeck = new ArrayList<TrainCard>(InitialGameSetUpVariables.getTrainCards());
        Collections.shuffle(this.trainCardDeck);
        this.trainCardDiscard = new ArrayList<TrainCard>();
        this.faceupTrainCardDeck = new ArrayList<TrainCard>();

        for (int i = 0; i < 5; i++) {
            this.faceupTrainCardDeck.add(this.trainCardDeck.get(0));
            this.trainCardDeck.remove(0);
        }

        this.claimedRoutes = new ArrayList<Route>();
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

    public void reshuffleDiscardedTrainCards() {
        if (this.trainCardDiscard.size() == 0) {
            return;
        }
        Collections.shuffle(this.trainCardDiscard);
        this.trainCardDeck.addAll(this.trainCardDiscard);
        this.trainCardDiscard.clear();
    }

    public boolean PlayerDrawTrainCardFromDeck(String playerID) {
        if (this.getPlayer(playerID).DrawTrainCard(this.trainCardDeck.remove(0))) {
            if (this.trainCardDeck.size() == 0) {
                reshuffleDiscardedTrainCards();
            }
            return true;
        }
        return false;
    }

    public boolean PlayerDrawTrainCardFromFaceUp(String playerID, Integer cardIndex) {
        if (this.getPlayer(playerID).DrawTrainCard(this.faceupTrainCardDeck.get(cardIndex))) {
            if (this.trainCardDeck.size() > 0) {
                this.faceupTrainCardDeck.set(cardIndex, this.trainCardDeck.remove(0));
            } else {
                this.faceupTrainCardDeck.remove(cardIndex);
            }
        }
        return false;
    }

    public boolean PlayerClaimRoute(String playerID, Route routeClaimed) {
        if (this.claimedRoutes.contains(routeClaimed)) {
            return false;
        }
        if(this.getPlayer(playerID).ClaimRoute(routeClaimed)) {
            this.claimedRoutes.add(routeClaimed);
            return true;
        }
        return false;
    }

    public List<Route> RoutesClaimedByPlayer(String playerID) {
        return this.getPlayer(playerID).getRoutesClaimed();
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentPlayer);
    }

    private void finishTurn() {
        this.currentPlayer = this.players.indexOf(currentPlayer) + 1;
        if (currentPlayer == this.players.size()) {
            currentPlayer = 0;
        }
    }

    private void setFirstPlayer() {
        this.currentPlayer = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getTravelRate() > this.players.get(this.currentPlayer).getTravelRate()) {
                currentPlayer = i;
            }
        }
    }

    public boolean setPlayerTravelRate(String playerID, int travelRate) {
        this.getPlayer(playerID).setTravelRate(travelRate);
        boolean startGame = true;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getTravelRate() == null) {
                startGame = false;
            }
        }
        if (startGame) {
            this.start();
        }
        return true;
    }
}
