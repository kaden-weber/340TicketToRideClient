package weber.kaden.common.model;

import java.io.Serializable;

public class TrainCard implements Serializable {
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
