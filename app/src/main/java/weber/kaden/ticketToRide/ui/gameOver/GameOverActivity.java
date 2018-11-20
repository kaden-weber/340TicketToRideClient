package weber.kaden.ticketToRide.ui.gameOver;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Player;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.ui.gameList.GameListActivity;

public class GameOverActivity extends AppCompatActivity implements GameOverViewInterface {
	private GameOverActivity instance = this;
	private GameOverAdapter adapter;
	private GameOverPresenter gameOverPresenter = new GameOverPresenter(instance, new ClientFacade());
	private List<Player> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

	    RecyclerView recyclerView = findViewById(R.id.game_over_players_list);
	    recyclerView.setLayoutManager(new LinearLayoutManager(this));
	    adapter = new GameOverAdapter(this, playerList);
	    recyclerView.setAdapter(adapter);
	    Button quitGameButton = findViewById(R.id.game_over_quit_button);
	    quitGameButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
    }

    public class QuitGameTask extends AsyncTask<Void, Void, Boolean> {
    	private final String mUsername;
    	private final String mGameName;
    	private String errorString = "";

    	QuitGameTask(String username, String gameName) {
    		mUsername = username;
    		mGameName = gameName;
	    }

	    @Override
	    protected Boolean doInBackground(Void... voids) {
		    try {
		    	gameOverPresenter.quitGame(mUsername, mGameName);
		    }
		    catch (Exception e) {
		    	errorString = e.getMessage();
		    	System.out.println(errorString);
		    	return false;
		    }
		    return true;
	    }

	    @Override
	    protected void onPostExecute(Boolean success) {
		    if (success) {
		    	Intent intent = new Intent(instance, GameListActivity.class);
		    	startActivity(intent);
		    }
		    else {
		    	Toast.makeText(instance, errorString, Toast.LENGTH_SHORT).show();
		    }
	    }
    }
}
