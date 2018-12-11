package tests;

import com.example.flatfile.Serializer;
import com.google.gson.Gson;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandData.CommandDataClaimRoute;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.City;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCardType;

public class SerializerTests {
    private Gson gson;
    private Serializer serializer;
    private Model model;

    @BeforeEach
    public void setup(){
        gson = new Gson();
        model = Model.getInstance();
        Player p1 = new Player("p1", "password");
        Player p2 = new Player("p2", "password");
        Player p3 = new Player("p3", "password");

        model.addPlayer(p1);
        model.addPlayer(p2);
        model.addPlayer(p3);
        model.addGame(new Game(new ArrayList<Player>(Arrays.asList(p1, p2, p3)),
                "game1", "game1"));
        model.addGame(new Game(new ArrayList<Player>(Arrays.asList(p1, p3)),
                "game2", "game2"));

        CommandData c1 = new CommandDataClaimRoute(new ArrayList<String>(Arrays.asList("game1", "p1")),
                CommandType.CLAIMROUTE, new Route(City.KANSAS_CITY, City.OKLAHOMA_CITY, 10,
                TrainCardType.BOX, false), TrainCardType.BOX);
        CommandData c2 = new CommandData(new ArrayList<String>(Arrays.asList("game1", "p1")),
                CommandType.DRAWTRAINCARDFROMDECK);
        model.addGameHistoryToGame("game1", c1);
        model.addGameHistoryToGame("game1", c2);

        serializer = new Serializer();
    }

    @AfterAll
    public static void tearDown() {

    }

    @Test
    public void testPlayerDeserialization() {
        String serialized = gson.toJson(model.getPlayers());
        System.out.println(serialized);
        List<Player> players = serializer.deserializePlayers(serialized);
        Iterator<Player> iterator = players.iterator();
        // just debug to see the contents of the list... I'm lazy
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

    @Test
    public void testGameDeserialization() {
        String serialized = gson.toJson(model.getGames());
        System.out.println(serialized);
        List<Game> games = serializer.deserializeGames(serialized);
        Iterator<Game> iterator = games.iterator();
        // just debug to see the contents of the list... I'm lazy
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

    @Test
    public void testCommandDataDeserialization() {
        String serialized = gson.toJson(model.getGameHistory("game1"));
        System.out.println(serialized);
        List<CommandData> history = serializer.deserializeCommandData(serialized);
        Iterator<CommandData> iterator = history.iterator();
        // just debug to see the contents of the list... I'm lazy
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }

}
