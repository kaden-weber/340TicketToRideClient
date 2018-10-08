package weber.kaden.myapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import weber.kaden.myapplication.R;

public class GameLobbyActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        String gameId= getIntent().getStringExtra("GAME_ID");
        setTitle(gameId);
        Button quitButton = (Button) findViewById(R.id.exit_game);
        Button startButton = (Button) findViewById(R.id.start_game);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GameLobbyActivity.this, "Goodbye!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Game, view or Fragment
            }
        });

    }

}
