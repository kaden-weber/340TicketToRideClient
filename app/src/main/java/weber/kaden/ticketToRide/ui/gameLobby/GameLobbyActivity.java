package weber.kaden.ticketToRide.ui.gameLobby;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.serverProxy.Poller;
import weber.kaden.ticketToRide.ui.GameSetupActivity;

public class GameLobbyActivity  extends AppCompatActivity implements GameLobbyViewInterface {

    private StartGameTask startGameTask = null;
    private QuitGameTask quitGameTask = null;
    GameLobbyActivity instance = this;
    GameLobbyAdapter adapter;
    ClientFacade clientFacade = new ClientFacade();
    GameLobbyPresenter gameLobbyPresenter = new GameLobbyPresenter(this, clientFacade);
    private List<Player> playerList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerList.addAll(Model.getInstance().getCurrentGame().getPlayers());
        setContentView(R.layout.activity_game_lobby);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamelobby_players);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameLobbyAdapter(this, playerList);
        recyclerView.setAdapter(adapter);

        String gameName= getIntent().getStringExtra("GAME_NAME");
        setTitle(gameName);
        final Button quitButton = (Button) findViewById(R.id.exit_game);
        Button startButton = (Button) findViewById(R.id.start_game);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitGameTask = new GameLobbyActivity.QuitGameTask();
                quitGameTask.execute((Void) null);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showProgress(true);
                startGameTask = new GameLobbyActivity.StartGameTask();
                startGameTask.execute((Void) null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Poller.getInstance(this).pollGame();
    }
    @Override
    public void updatePlayersList(List<Player> players) {
        playerList.clear();
        playerList.addAll(players);
        adapter.notifyDataSetChanged();
    }

    public class StartGameTask extends AsyncTask<Void, Void, Boolean> {

        private String errorString = "";

        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameLobbyPresenter gameLobbyPresenter = new GameLobbyPresenter(instance, clientFacade);

            try {
                gameLobbyPresenter.startGame();
                Intent intent = new Intent(instance, GameSetupActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                errorString = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            startGameTask = null;
            //showProgress(false);

            if (success) {
                Toast.makeText(instance, "Start game successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(instance, errorString, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            startGameTask = null;
            //showProgress(false);
        }
    }

    public void setupGame() {
        //Toast.makeText(GameLobbyActivity.this, "Game Started!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(instance, GameSetupActivity.class);
        startActivity(intent);

    }

    public class QuitGameTask extends AsyncTask<Void, Void, Boolean> {

        private String errorString = "";


        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameLobbyPresenter gameLobbyPresenter = new GameLobbyPresenter(instance, clientFacade);

            try {
                gameLobbyPresenter.exitLobby();
            } catch (Exception e) {
                errorString = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            startGameTask = null;
            //showProgress(false);

            if (success) {
                Toast.makeText(GameLobbyActivity.this, "Goodbye!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(instance, errorString, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            startGameTask = null;
            //showProgress(false);
        }
    }
}
