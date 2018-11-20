package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.lang.*;
import java.util.Objects;
import java.util.UUID;

import weber.kaden.common.command.CommandData.CommandData;

public class Game {
    private List<Player> players;
    private String ID;
    private String gameName;
    private boolean destinationCardsDealt;
    private List<ChatMessage> chat = new ArrayList<>();
    private List<DestinationCard> destinationCardDeck;
    private List<DestinationCard> destinationCardDiscard;
    private List<TrainCard> trainCardDeck;
    private List<TrainCard> trainCardDiscard;
    private List<TrainCard> faceupTrainCardDeck;
    private List<Route> claimedRoutes;
    private List<Route> unclaimedRoutes;
    private int currentPlayer;
    private GameState gameState;
    private List<CommandData> gameHistory;

    public Game() {
        this.players = new ArrayList<Player>();
        this.ID = UUID.randomUUID().toString();
        this.destinationCardsDealt = false;
    }

    public Game(List<Player> players, String ID, String gameName) {
        this.players = players;
        this.ID = ID;
        this.gameName = gameName;
        this.destinationCardsDealt = false;
        this.gameState = new GameNotStartedState();
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

    public boolean isSetup() {
        return gameState.isSetup();
    }

    public void setSetup(boolean setup) {
        if (setup) {
        	gameState = new GameSetupState();
        }
    }

    public boolean isStarted() {
        return gameState.isStarted();
    }

    public void setStarted(boolean started) {
        if (started) {
        	gameState = new GamePlayingState();
        }
    }

    public boolean isDestinationCardsDealt() {
        return destinationCardsDealt;
    }

    public void setDestinationCardsDealt(boolean destinationCardsDealt) {
        this.destinationCardsDealt = destinationCardsDealt;
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
    public int hashCode() {

        return Objects.hash(players, ID, gameName, gameState.isStarted());
    }

    public boolean setUp() {
        if (this.getPlayers().size() < 2 || this.getPlayers().size() > 5) {
            return false;
        }
        InitalizeDecks();
        AssignColors();
        setSetup(true);
        return true;
    }

    public boolean dealDestinationCards() {
        DealTrainCardsToPlayers();
        DealDestinationCardsToPlayers();
        setFirstPlayer();
        setDestinationCardsDealt(true);
        return true;
    }

    public boolean start() {
        setStarted(true);
        return true;
    }

    private void AssignColors() {
        this.players.get(0).setColor(PlayerColors.PLAYER_BLACK);
        if (this.players.size() > 1) {
            this.players.get(1).setColor(PlayerColors.PLAYER_BLUE);
            if (this.players.size() > 2) {
                this.players.get(2).setColor(PlayerColors.PLAYER_GREEN);
                if (this.players.size() > 3) {
                    this.players.get(3).setColor(PlayerColors.PLAYER_RED);
                    if (this.players.size() > 4) {
                        this.players.get(4).setColor(PlayerColors.PLAYER_YELLOW);
                    }
                }
            }
        }
    }

    private void InitalizeDecks() {
        this.destinationCardDeck = new ArrayList<DestinationCard>(InitialGameSetUpVariables.getDestinationCards());
        Collections.shuffle(this.destinationCardDeck);
        this.destinationCardDiscard = new ArrayList<DestinationCard>();

        this.trainCardDeck = new ArrayList<TrainCard>(InitialGameSetUpVariables.getTrainCards());
        Collections.shuffle(this.trainCardDeck);
        this.trainCardDiscard = new ArrayList<TrainCard>();
        this.faceupTrainCardDeck = new ArrayList<TrainCard>();
        this.dealFaceUpCards();

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
                cards.add(this.trainCardDeck.remove(0));
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
            cards.add(this.destinationCardDeck.remove(0));
        }
        player.DealDestinationCards(cards);
    }

    public boolean PlayerDrawDestinationCards(String playerID, List<DestinationCard> cards) {
        this.destinationCardDeck.removeAll(cards);
        boolean toReturn = this.getPlayer(playerID).DrawDestinationCards(cards);
        if (!isStarted()) {
            boolean startGame = true;
            for (int i = 0; i < this.players.size(); i++) {
                if (this.players.get(i).getDestinationCardHand().size() == 0) {
                    startGame = false;
                }
            }
            if (startGame) {
                this.start();
            }
        }
        return toReturn;
    }

    public List<DestinationCard> getTopOfDestinationCardDeck() {
        List<DestinationCard> toReturn = new ArrayList<DestinationCard>();
        if (this.destinationCardDeck.size() == 0) {
            return null;
        } else if (this.destinationCardDeck.size() > 0) {
            toReturn.add(this.destinationCardDeck.get(0));
        } if (this.destinationCardDeck.size() > 1) {
            toReturn.add(this.destinationCardDeck.get(1));
        } if (this.destinationCardDeck.size() > 2) {
            toReturn.add(this.destinationCardDeck.get(2));
        }
        return toReturn;
    }

    public boolean DiscardDestionationCards(List<DestinationCard> cards) {
        if (cards.size() == 0) {
            return true;
        }
        this.destinationCardDeck.removeAll(cards);
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

    public List<TrainCard> getFaceUpTrainCardDeck() {
        return faceupTrainCardDeck;
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

    public boolean PlayerDrawTrainCardFromFaceUp(String playerID, int cardIndex) {
        if (this.getPlayer(playerID).DrawTrainCard(this.faceupTrainCardDeck.get(cardIndex))) {
            if (this.trainCardDeck.size() > 0) {
                this.faceupTrainCardDeck.set(cardIndex, this.trainCardDeck.remove(0));
            } else {
                this.faceupTrainCardDeck.remove(cardIndex);
            }
            this.checkFaceUpCards();
            return true;
        }
        return false;
    }

    private void checkFaceUpCards() {
        if (this.faceupTrainCardDeck.size() > 3) {
            int num = 0;
            for (int i = 0; i < this.faceupTrainCardDeck.size(); i++) {
                if (this.faceupTrainCardDeck.get(i).getType().equals(TrainCardType.LOCOMOTIVE)) {
                    num++;
                }
            }
            if (num >= 3) {
                this.trainCardDiscard.addAll(this.faceupTrainCardDeck);
                this.faceupTrainCardDeck.clear();
                this.dealFaceUpCards();
            }
        }
    }

    public void dealFaceUpCards() {
        int num = 5;
        if (this.trainCardDeck.size() < 5) {
            num = this.trainCardDeck.size();
        }
        for (int i = 0; i < num; i++) {
            this.faceupTrainCardDeck.add(this.trainCardDeck.get(0));
            this.trainCardDeck.remove(0);
        }
        if (this.trainCardDeck.size() == 0) {
            reshuffleDiscardedTrainCards();
        }
    }

    public boolean PlayerClaimRoute(String playerID, Route routeClaimed, TrainCardType cardType) {
        if (this.claimedRoutes.contains(routeClaimed)) {
            return false;
        }
        if(this.getPlayer(playerID).ClaimRoute(routeClaimed)) {
            this.claimedRoutes.add(routeClaimed);
            this.trainCardDiscard.addAll(this.getPlayer(playerID).useTrainCards(routeClaimed, cardType));
            return true;
        }
        return false;
    }

    public List<Route> RoutesClaimedByPlayer(String playerID) {
        return this.getPlayer(playerID).getRoutesClaimed();
    }

    public String getCurrentPlayer() {
        return this.players.get(currentPlayer).getID();
    }
    public void setCurrentPlayer(String player){
        for(int i = 0; i < this.players.size(); i++){
            if (player.equals(players.get(i))){
                this.currentPlayer = i;
            }
        }
    }

    public boolean finishTurn() {
        this.currentPlayer++;
        if (currentPlayer == this.players.size()) {
            currentPlayer = 0;
        }
        return true;
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
        boolean dealDestinationCards = true;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getTravelRate() == null) {
                dealDestinationCards = false;
            }
        }
        if (dealDestinationCards) {
            this.dealDestinationCards();
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return gameState.isStarted() == game.isStarted() &&
                gameState.isSetup() == game.isSetup() &&
                currentPlayer == game.currentPlayer &&
                Objects.equals(players, game.players) &&
                Objects.equals(ID, game.ID) &&
                Objects.equals(gameName, game.gameName) &&
                Objects.equals(chat, game.chat) &&
                Objects.equals(destinationCardDeck, game.destinationCardDeck) &&
                Objects.equals(destinationCardDiscard, game.destinationCardDiscard) &&
                Objects.equals(trainCardDeck, game.trainCardDeck) &&
                Objects.equals(trainCardDiscard, game.trainCardDiscard) &&
                Objects.equals(faceupTrainCardDeck, game.faceupTrainCardDeck) &&
                Objects.equals(claimedRoutes, game.claimedRoutes) &&
                Objects.equals(unclaimedRoutes, game.unclaimedRoutes);
    }

    public boolean PlayerDiscardTrainCard(String playerID, TrainCard card) {
        return this.getPlayer(playerID).DiscardTrainCard(card);
    }

    public TrainCard getTopOfTrainCardDeck() {
        if (this.trainCardDeck.size() == 0) {
            return null;
        }
        else {
            return this.trainCardDeck.get(0);
        }
     }

    public boolean PlayerRemoveDestinationCard(String playerID, DestinationCard card) {
        return this.getPlayer(playerID).removeDestinationCard(card);
    }

    public boolean RemoveTrainCarsFromPlayer(String playerID) {
        return this.getPlayer(playerID).testRemoveTrainCars();
    }

    public boolean endGame() {
        //calculate longest route
        int playerWithLongestPath = 0;
        int longestPathSoFar = 0;
        for (int i = 0; i < this.players.size(); i++) {
            for (Route startRoute : this.players.get(i).getRoutesClaimed()) {
                int score = getLongestPath(startRoute, i, startRoute.getCity1());
                int score2 = getLongestPath(startRoute, i, startRoute.getCity2());
                if (score2 > score) {
                    score = score2;
                }
                if (score > longestPathSoFar) {
                    longestPathSoFar = score;
                    playerWithLongestPath = i;
                }
            }
        }
        this.players.get(playerWithLongestPath).setLongestPath(true);
        return true;
    }

    private int getLongestPath(Route startRoute, int playerInt, City parentCity) {
        int max = 0;
        int dist = 0;
        City newParentCity;
        if (startRoute.getCity1().equals(parentCity)) {
            newParentCity = startRoute.getCity2();
        } else {
            newParentCity = startRoute.getCity1();
        }
        startRoute.setVisited(true);
        for (Route otherRoute : this.players.get(playerInt).getRoutesClaimed()) {
            if (!otherRoute.isVisited()) {
                if (this.routesAreConnected(startRoute, otherRoute, parentCity)) {
                    dist = otherRoute.getScore() + getLongestPath(otherRoute, playerInt, newParentCity);
                    if (dist > max) {
                        max = dist;
                    }
                }
            }
        }
        startRoute.setVisited(false);
        return max;
    }

    private boolean routesAreConnected(Route startRoute, Route otherRoute, City parentCity) {
        return ((startRoute.getCity1().equals(otherRoute.getCity1()) && !startRoute.getCity1().equals(parentCity))||
                (startRoute.getCity2().equals(otherRoute.getCity1())  && !startRoute.getCity2().equals(parentCity)) ||
                (startRoute.getCity1().equals(otherRoute.getCity2())  && !startRoute.getCity1().equals(parentCity)) ||
                (startRoute.getCity2().equals(otherRoute.getCity2())  && !startRoute.getCity2().equals(parentCity)));
    }

    public void updateGameState() {
        switch (gameState.type) {
            case "GameNotStartedState":
                gameState = new GameNotStartedState();
                break;
            case "GameSetupState":
                gameState = new GameSetupState();
                break;
            case "GamePlayingState":
                gameState = new GamePlayingState();
                break;
            case "GameLastRoundState":
                gameState = new GameLastRoundState();
                break;
            case "GameOverState":
                gameState = new GameOverState();
                break;
        }
    }

    public void addHistory(CommandData history) {
        if (this.gameHistory == null) {
            this.gameHistory = new ArrayList<CommandData>();
        }
        this.gameHistory.add(history);
    }

    public List<CommandData> getGameHistory() {
        return gameHistory;
    }

    private class GameState {
        private String type;

        GameState(String type) {
            this.type = type;
        }

        boolean isSetup() {
        	return false;
        }
        boolean isStarted() {
        	return false;
        }
        boolean isFinalRound() {
        	return false;
        }
        boolean isGameOver() {
        	return false;
        }
    }

    private class GameNotStartedState extends GameState {
        GameNotStartedState() {
            super("GameNotStartedState");
        }
    }

    private class GameSetupState extends GameState {
        GameSetupState() {
            super("GameSetupState");
        }

        @Override
        boolean isSetup() {
            return true;
        }
    }

    private class GamePlayingState extends GameState {
        GamePlayingState() {
            super("GamePlayingState");
        }

        @Override
        boolean isStarted() {
            return true;
        }
    }

    private class GameLastRoundState extends GameState {
        GameLastRoundState() {
            super("GameLastRoundState");
        }

        @Override
        boolean isFinalRound() {
            return true;
        }
    }

    private class GameOverState extends GameState {
        GameOverState() {
            super("GameOverState");
        }

        @Override
        boolean isGameOver() {
            return true;
        }
    }
}