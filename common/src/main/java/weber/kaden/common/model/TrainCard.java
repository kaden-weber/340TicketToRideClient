package weber.kaden.common.model;

public class TrainCard {
    private TrainCardType type;

    public TrainCard(TrainCardType type) {
        this.type = type;
    }

    public TrainCardType getType() {
        return type;
    }

    public void setType(TrainCardType type) {
        this.type = type;
    }
}
