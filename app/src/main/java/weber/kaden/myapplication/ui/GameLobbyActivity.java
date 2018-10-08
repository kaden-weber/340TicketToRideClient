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

public class GameLobbyActivity  extends AppCompatActivity {

    private StartGameTask startGameTask = null;
    private QuitGameTask quitGameTask = null;
    GameLobbyActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        String gameId= getIntent().getStringExtra("GAME_ID");
        setTitle(gameId);
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
