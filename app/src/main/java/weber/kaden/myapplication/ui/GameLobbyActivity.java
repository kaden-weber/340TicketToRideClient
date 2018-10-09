package weber.kaden.myapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.serverProxy.Poller;

public class GameLobbyActivity  extends AppCompatActivity {

    private StartGameTask startGameTask = null;
    private QuitGameTask quitGameTask = null;
    GameLobbyActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
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
        Poller.getInstance(this).startGamesPolling();
    }

    @Override
    public void onPause() {
        Poller.getInstance(this).stopGamesPolling();
        super.onPause();
    }

    public class StartGameTask extends AsyncTask<Void, Void, Boolean> {

        private String errorString = "";

        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameLobbyPresenter gameLobbyPresenter = new GameLobbyPresenter(instance, clientFacade);

            try {
                gameLobbyPresenter.startGame();
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

    public void startGame() {
        Toast.makeText(GameLobbyActivity.this, "Game Started!", Toast.LENGTH_SHORT).show();
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
