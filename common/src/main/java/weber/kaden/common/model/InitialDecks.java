package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialDecks {
    private static List<DestinationCard> destinationCards = new ArrayList<DestinationCard>(Arrays.asList(
            new DestinationCard(City.DENVER, City.EL_PASO, 4),
            new DestinationCard(City.KANSAS_CITY, City.HOUSTON, 5),
            new DestinationCard(City.NEW_YORK, City.ATLANTA, 6),
            new DestinationCard(City.CALGARY, City.SALT_LAKE_CITY, 7),
            new DestinationCard(City.CHICAGO, City.NEW_ORLEANS, 7),
            new DestinationCard(City.HELENA, City.LOS_ANGELES, 8),
            new DestinationCard(City.SAULT_ST_MARIE, City.NASHVILLE, 8),
            new DestinationCard(City.DULUTH, City.HOUSTON, 8),
            new DestinationCard(City.MONTREAL, City.ATLANTA, 9),
            new DestinationCard(City.SEATTLE, City.LOS_ANGELES, 9),
            new DestinationCard(City.CHICAGO, City.SANTA_FE, 9),
            new DestinationCard(City.SAULT_ST_MARIE, City.OKLAHOMA_CITY, 9),
            new DestinationCard(City.TORONTO, City.MIAMI, 10),
            new DestinationCard(City.DULUTH, City.EL_PASO, 10),
            new DestinationCard(City.WINNIPEG, City.LITTLE_ROCK, 11),
            new DestinationCard(City.DALLAS, City.NEW_YORK, 11),
            new DestinationCard(City.PORTLAND, City.PHOENIX, 11),
            new DestinationCard(City.DENVER, City.PITTSBURGH, 11),
            new DestinationCard(City.BOSTON, City.MIAMI, 12),
            new DestinationCard(City.WINNIPEG, City.HOUSTON, 12),
            new DestinationCard(City.VANCOUVER, City.SANTA_FE, 13),
            new DestinationCard(City.MONTREAL, City.NEW_ORLEANS, 13),
            new DestinationCard(City.CALGARY, City.PHOENIX, 13),
            new DestinationCard(City.LOS_ANGELES, City.CHICAGO, 16),
            new DestinationCard(City.PORTLAND, City.NASHVILLE, 17),
            new DestinationCard(City.SAN_FRANCISCO, City.ATLANTA, 17),
            new DestinationCard(City.VANCOUVER, City.MONTREAL, 20),
            new DestinationCard(City.LOS_ANGELES, City.MIAMI, 20),
            new DestinationCard(City.LOS_ANGELES, City.NEW_YORK, 21),
            new DestinationCard(City.SEATTLE, City.NEW_YORK, 22)
    ));

    public static List<DestinationCard> getDestinationCards() {return destinationCards;}

    public static List<TrainCard> getTrainCards () {

        List<TrainCard> trainCards = new ArrayList<TrainCard>();

        for (int i = 0; i < 12; i++) {
            trainCards.add(new TrainCard(TrainCardType.BOX));
            trainCards.add(new TrainCard(TrainCardType.PASSENGER));
            trainCards.add(new TrainCard(TrainCardType.TANKER));
            trainCards.add(new TrainCard(TrainCardType.REEFER));
            trainCards.add(new TrainCard(TrainCardType.FREIGHT));
            trainCards.add(new TrainCard(TrainCardType.HOPPER));
            trainCards.add(new TrainCard(TrainCardType.COAL));
            trainCards.add(new TrainCard(TrainCardType.CABOOSE));
            trainCards.add(new TrainCard(TrainCardType.LOCOMOTIVE));
        }

        trainCards.add(new TrainCard(TrainCardType.LOCOMOTIVE));
        trainCards.add(new TrainCard(TrainCardType.LOCOMOTIVE));

        return trainCards;
    }
}
