package weber.kaden.myapplication.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import weber.kaden.myapplication.R;
import weber.kaden.myapplication.ui.gameView.GameActivity;

public class GameSetupActivity extends FragmentActivity implements GameSetupViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v4.app.DialogFragment fragmentSetup = new FragmentSetup();
        fragmentSetup.show(getSupportFragmentManager(), "GameSetupFragment");
        setContentView(R.layout.activity_game_setup);
    }
    public void startGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
