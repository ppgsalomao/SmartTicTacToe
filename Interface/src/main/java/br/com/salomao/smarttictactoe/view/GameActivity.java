package br.com.salomao.smarttictactoe.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;

import br.com.salomao.smarttictactoe.R;
import br.com.salomao.smarttictactoe.model.GameConfiguration;

public class GameActivity extends AppCompatActivity {

    private static final String GAME_CONFIGURATION_EXTRA_KEY = "GameConfiguration";

    private GameConfiguration gameConfiguration;

    public static void startActivity(Context context, GameConfiguration gameConfiguration) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(GAME_CONFIGURATION_EXTRA_KEY, gameConfiguration);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Serializable gameConfigurationSerializableExtra =
                this.getIntent().getSerializableExtra(GAME_CONFIGURATION_EXTRA_KEY);
        if(gameConfigurationSerializableExtra != null
                && gameConfigurationSerializableExtra instanceof GameConfiguration) {
            this.gameConfiguration = (GameConfiguration) gameConfigurationSerializableExtra;
        }
    }



}
