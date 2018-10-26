package weber.kaden.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import weber.kaden.myapplication.R;

public class GameSetupActivity extends AppCompatActivity implements GameSetupViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    }
}
