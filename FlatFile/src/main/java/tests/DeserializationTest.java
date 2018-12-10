package tests;

import com.example.flatfile.Serializer;
import com.google.gson.Gson;

import org.junit.jupiter.api.*;

import java.util.Iterator;
import java.util.List;

import weber.kaden.common.model.Player;

public class DeserializationTest {
    private String jsonString;
    private Gson gson;
    private Serializer serializer;

    @BeforeEach
    public void setup(){
        gson = new Gson();
        jsonString =
            "{\"players\": [{\"STARTING_TRAIN_PIECES\": 10, " +
                "\"ID\": \"test\"," +
                "\"password\": \"test\", " +
                "\"dealtDestinationCards\": [" +
                    "{\"startCity\": VANCOUVER, " +
                    "\"endCity\": CALGARY, " +
                    "\"points\": 10}, " +
                    "{\"startCity\": WINNIPEG, " +
                    "\"endCity\": PORTLAND, " +
                    "\"points\": 15}], " +
                "\"destinationCardHand\": [" +
                    "{\"startCity\": SEATTLE, " +
                    "\"endCity\": HELENA, " +
                    "\"points\": 5}, " +
                    "{\"startCity\": WASHINGTON, " +
                    "\"endCity\": PITTSBURGH, " +
                    "\"points\": 7}], " +
                "\"trainCards\": [" +
                    "{\"type\": BOX}, " +
                    "{\"type\": PASSENGER}], " +
                "\"routesClaimed\": [" +
                    "{\"city1\": PHOENIX, " +
                    "\"city2\": DULUTH, " +
                    "\"score\": 1, " +
                    "\"cost\": 2, " +
                    "\"type\": REEFER, " +
                    "\"isSecondRoute\": false, " +
                    "\"visited\": false}], " +
                "\"trainPieces\": 10, " +
                "\"score\": 10, " +
                "\"color\": PLAYER_GREEN, " +
                "\"TravelRate\": 3, " +
                "\"hasLongestPath\": false, " +
                "\"destinationCardPoints\": 5, " +
                "\"destinationCardPointsLost\": 5}, " +
            "{\"STARTING_TRAIN_PIECES\": 10, " +
                "\"ID\": \"test\"," +
                "\"password\": \"test\", " +
                "\"dealtDestinationCards\": [" +
                    "{\"startCity\": VANCOUVER, " +
                    "\"endCity\": CALGARY, " +
                    "\"points\": 10}, " +
                    "{\"startCity\": WINNIPEG, " +
                    "\"endCity\": PORTLAND, " +
                    "\"points\": 15}], " +
                "\"destinationCardHand\": [" +
                    "{\"startCity\": SEATTLE, " +
                    "\"endCity\": HELENA, " +
                    "\"points\": 5}, " +
                    "{\"startCity\": WASHINGTON, " +
                    "\"endCity\": PITTSBURGH, " +
                    "\"points\": 7}], " +
                "\"trainCards\": [" +
                    "{\"type\": BOX}, " +
                    "{\"type\": PASSENGER}], " +
                "\"routesClaimed\": [" +
                    "{\"city1\": PHOENIX, " +
                    "\"city2\": DULUTH, " +
                    "\"score\": 1, " +
                    "\"cost\": 2, " +
                    "\"type\": REEFER, " +
                    "\"isSecondRoute\": false, " +
                    "\"visited\": false}], " +
                "\"trainPieces\": 10, " +
                "\"score\": 10, " +
                "\"color\": PLAYER_GREEN, " +
                "\"TravelRate\": 3, " +
                "\"hasLongestPath\": false, " +
                "\"destinationCardPoints\": 5, " +
                "\"destinationCardPointsLost\": 5}]}";
        serializer = new Serializer();
    }

    @AfterAll
    public static void tearDown() {

    }

    @Test
    public void testPlayerDeserialization() {
        List<Player> players = serializer.deserializePlayers(jsonString);
        Iterator<Player> iterator = players.iterator();
        // just debug to see the contents of the list... I'm lazy
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

}
