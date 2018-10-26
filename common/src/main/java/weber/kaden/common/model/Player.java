package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private String ID;
    private String password;
    private List<DestinationCard> dealtDestinationCards;
    private List<DestinationCard> destinationCardHand;
    private List<TrainCard> trainCards;

    public Player(String ID, String password) {
        this.ID = ID;
        this.password = password;
        this.dealtDestinationCards = new ArrayList<DestinationCard>();
        this.destinationCardHand = new ArrayList<DestinationCard>();
        this.trainCards = new ArrayList<TrainCard>();
    }

    public Player(String ID, String password, List<DestinationCard> dealtDestinationCards, List<DestinationCard> destinationCardHand, List<TrainCard> trainCards) {
        this.ID = ID;
        this.password = password;
        this.dealtDestinationCards = dealtDestinationCards;
        this.destinationCardHand = destinationCardHand;
        this.trainCards = trainCards;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DestinationCard> getDealtDestinationCards() {
        return dealtDestinationCards;
    }

    public void setDealtDestinationCards(List<DestinationCard> dealtDestinationCards) {
        this.dealtDestinationCards = dealtDestinationCards;
    }

    public List<DestinationCard> getDestinationCardHand() {
        return destinationCardHand;
    }

    public void setDestinationCardHand(List<DestinationCard> destinationCardHand) {
        this.destinationCardHand = destinationCardHand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(ID, player.ID) &&
                Objects.equals(password, player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, password);
    }

    public boolean DrawDestinationCards (List<DestinationCard> cards) {
        this.dealtDestinationCards.clear();
        return this.destinationCardHand.addAll(cards);
    }

    public boolean DealDestinationCards(List<DestinationCard> cards) {
        return this.dealtDestinationCards.addAll(cards);
    }

    public boolean DealTrainCards(List<TrainCard> cards) {
        return this.trainCards.addAll(cards);
    }

    public boolean DrawTrainCard(TrainCard card) {
        return this.trainCards.add(card);
    }

    public boolean DiscardTrainCard(TrainCard card) {
        return this.trainCards.remove(card);
    }
}
