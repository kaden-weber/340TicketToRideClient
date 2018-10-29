package weber.kaden.myapplication.ui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import weber.kaden.myapplication.R;
public class GameSetupActivity extends FragmentActivity implements GameSetupViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v4.app.DialogFragment fragmentSetup = new FragmentSetup();
        fragmentSetup.show(getSupportFragmentManager(), "GameSetupFragment");

        setContentView(R.layout.activity_game_setup);
    }

}
