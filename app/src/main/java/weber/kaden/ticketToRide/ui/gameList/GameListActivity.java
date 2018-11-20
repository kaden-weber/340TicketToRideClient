package weber.kaden.ticketToRide.ui.gameList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.serverProxy.Poller;
import weber.kaden.ticketToRide.ui.gameLobby.GameLobbyActivity;

public class GameListActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener, GameListViewInterface{
    private GameListActivity instance = this;
    private ClientFacade clientFacade = new ClientFacade();
    String m_Text = "";
    GameListAdapter adapter;
    GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
    private List<Game> gamesList = new ArrayList<>();
    String currentGameName = "";

    @Override
    public void updateGamesList(List<Game> games) {
        gamesList.clear();
        gamesList.addAll(games);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamelist_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameListAdapter(this, gamesList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        Button createGameButton = (Button) findViewById(R.id.create_game_button);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    AlertDialog.Builder builder = new AlertDialog.Builder(instance);
                    builder.setTitle("Please Enter Game Name");
                    // Set up the input
                    final EditText input = new EditText(instance);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            CreateGameTask createGameTask = new CreateGameTask(getIntent().getStringExtra("USERNAME"), m_Text);
                            createGameTask.execute((Void) null);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Poller.getInstance(this).pollGamesList();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "Joining " + adapter.getItem(0).getGameName(), Toast.LENGTH_SHORT).show();
        //set current game to game
        Model model = Model.getInstance();
        model.setCurrentGame(adapter.getItem(position));
        JoinGameTask joinGameTask = new JoinGameTask(Model.getInstance().getCurrentUser(), Model.getInstance().getCurrentGame().getID());
        joinGameTask.execute((Void) null);
        currentGameName = adapter.getGameName(position);
    }
    public class CreateGameTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mgameName;
        private String errorString = "";

        CreateGameTask(String username, String gameName) {
            mUsername = username;
            mgameName = gameName;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
            try {
                Game game = gameListPresenter.createGame(mUsername, mgameName);
            } catch (Exception e) {
                errorString = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
    public class JoinGameTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mgameName;
        private String errorString = "";

        JoinGameTask(String username, String gameName) {
            mUsername = username;
            mgameName = gameName;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
            try {
                gameListPresenter.joinGame(mUsername, mgameName);
            } catch (Exception e) {
                errorString = e.getMessage();
                System.out.println(errorString);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                Intent intent = new Intent(instance, GameLobbyActivity.class);
                intent.putExtra("GAME_NAME", currentGameName);
                intent.putExtra("GAME_ID", currentGameName);
                startActivity(intent);
            } else {
                Toast.makeText(instance, errorString, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}

