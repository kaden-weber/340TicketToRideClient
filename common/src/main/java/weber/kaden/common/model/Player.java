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
    private List<Route> routesClaimed;
    private Integer trainPieces;
    private Integer score;
    private PlayerColors color;
    private Integer TravelRate = null;

    public Player(String ID, String password) {
        this.ID = ID;
        this.password = password;
        this.dealtDestinationCards = new ArrayList<DestinationCard>();
        this.destinationCardHand = new ArrayList<DestinationCard>();
        this.trainCards = new ArrayList<TrainCard>();
        this.routesClaimed = new ArrayList<Route>();
        this.trainPieces = 40;
        this.score = 0;
    }

    public Player(String ID, String password, List<DestinationCard> dealtDestinationCards, List<DestinationCard> destinationCardHand, List<TrainCard> trainCards, List<Route> routes, Integer trainPieces, Integer score) {
        this.ID = ID;
        this.password = password;
        this.dealtDestinationCards = dealtDestinationCards;
        this.destinationCardHand = destinationCardHand;
        this.trainCards = trainCards;
        this.routesClaimed = routes;
        this.trainPieces = trainPieces;
        this.score = score;
    }

    public Player(Player player) {
        this.ID = player.getID();
        this.password = player.getPassword();
        this.dealtDestinationCards = new ArrayList<DestinationCard>();
        this.destinationCardHand = new ArrayList<DestinationCard>();
        this.trainCards = new ArrayList<TrainCard>();
        this.routesClaimed = new ArrayList<Route>();
        this.trainPieces = 40;
        this.score = 0;
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

    public PlayerColors getColor() {
        return color;
    }

    public void setColor(PlayerColors color) {
        this.color = color;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getNumberOfTrainCards() {return this.trainCards.size();}

    public Integer getNumberOfTrains() {return this.trainPieces;}

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public Integer getNumberOfDestinationCards() {return this.destinationCardHand.size();}

    public List<DestinationCard> getDealtDestinationCards() {
        return this.dealtDestinationCards;
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

    public boolean ClaimRoute(Route routeClaimed) {
        if (routeClaimed.getCost() > this.trainPieces) {
            return false;
        }
        if(this.routesClaimed.add(routeClaimed)) {
            int numberOfCards = routeClaimed.getCost();
            for (int i = 0; i < this.trainCards.size(); i++) {
                if (numberOfCards > 0 && this.trainCards.get(i).getType().equals(routeClaimed.getType())) {
                    this.trainCards.remove(i);
                    numberOfCards--;
                    i--;
                }
            }
            if (numberOfCards > 0) {
                for (int i = 0; i < this.trainCards.size(); i++) {
                    if (numberOfCards > 0 && this.trainCards.get(i).getType().equals(TrainCardType.LOCOMOTIVE)) {
                        this.trainCards.remove(i);
                        numberOfCards--;
                        i--;
                    }
                }
            }
            this.trainPieces -= routeClaimed.getCost();
            this.score += routeClaimed.getScore();
            return true;
        } else {
            return false;
        }
    }

    public List<Route> getRoutesClaimed() {
        return routesClaimed;
    }

    public Integer getTravelRate() {
        return TravelRate;
    }

    public void setTravelRate(Integer travelRate) {
        TravelRate = travelRate;
    }

    public boolean hasTrainCards(int number, TrainCardType type) {
        int num = 0;
        for (int i = 0; i < this.trainCards.size(); i++) {
            if (this.trainCards.get(i).getType().equals(type) || this.trainCards.get(i).getType().equals(TrainCardType.LOCOMOTIVE))
            {
                num++;
            }
        }
        if (num >= number) {
            return true;
        }
        return false;
    }

    public boolean removeDestinationCard(DestinationCard card) {
        return this.destinationCardHand.remove(card);
    }

    public boolean testRemoveTrainCars() {
        this.trainPieces -= 5;
        return true;
    }
}
