package weber.kaden.myapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;

public class GameListActivity extends AppCompatActivity implements GameListAdapter.ItemClickListener{
    private GameListActivity instance = this;
    private ClientFacade clientFacade = new ClientFacade();
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
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Joining " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(instance, GameLobbyActivity.class);
        intent.putExtra("GAME_ID", adapter.getItem(position));
        startActivity(intent);
    }


}

