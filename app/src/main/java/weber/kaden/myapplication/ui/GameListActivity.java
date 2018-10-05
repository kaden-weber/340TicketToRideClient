package weber.kaden.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;

public class GameListActivity extends AppCompatActivity {
    private GameListActivity instance = this;
    private ClientFacade clientFacade = new ClientFacade();
    GameListPresenter gameListPresenter = new GameListPresenter(instance, clientFacade);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        //gameListPresenter.getGame()


    }

}
