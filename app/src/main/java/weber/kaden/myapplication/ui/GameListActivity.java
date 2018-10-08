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
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;

public class GameListActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener{
    private GameListActivity instance = this;
    private ClientFacade clientFacade = new ClientFacade();
    private String m_Text = "";
    GameListAdapter adapter;
    GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        List<Game> gamesList = gameListPresenter.displayGames();

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
                            CreateGameTask createGameTask = new CreateGameTask(getIntent().getStringExtra("USERNAME"), "GAMENAME");
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
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Joining " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(instance, GameLobbyActivity.class);
        intent.putExtra("GAME_ID", adapter.getItem(position));
        startActivity(intent);
    }
    public class CreateGameTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mgameName;
        private String errorString = "";

        CreateGameTask(String email, String gameName) {
            mEmail = email;
            mgameName = gameName;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
            try {
                gameListPresenter.createGame(mEmail);
            } catch (Exception e) {
                errorString = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            /*mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(instance, GameListActivity.class);
                intent.putExtra("USERNAME", mEmail);
                startActivity(intent);
            } else {
                Toast.makeText(instance, errorString, Toast.LENGTH_LONG).show();
            }*/
        }

        @Override
        protected void onCancelled() {

        }
    }

}

