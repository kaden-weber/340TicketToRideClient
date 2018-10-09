package weber.kaden.myapplication.ui;

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
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;
import weber.kaden.myapplication.serverProxy.Poller;

public class GameListActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener{
    private GameListActivity instance = this;
    private ClientFacade clientFacade = new ClientFacade();
    private String m_Text = "";
    GameListAdapter adapter;
    GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
    List<Game> gamesList = new ArrayList<>();
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
        Poller.getInstance(this).startGamesListPolling();
    }

    @Override
    public void onPause() {
        Poller.getInstance(this).stopGamesListPolling();
        super.onPause();
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("POSITION " + position);
        Toast.makeText(this, "Joining " + adapter.getItem(0), Toast.LENGTH_SHORT).show();
        //set current game to game
        Model model = Model.getInstance();
        model.setCurrentGame(adapter.getItem(position));
        Intent intent = new Intent(instance, GameLobbyActivity.class);
        intent.putExtra("GAME_NAME", adapter.getGameName(position));
        intent.putExtra("GAME_ID", adapter.getItem(position).getGameName());
        startActivity(intent);
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
                System.out.println(errorString);
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

}

