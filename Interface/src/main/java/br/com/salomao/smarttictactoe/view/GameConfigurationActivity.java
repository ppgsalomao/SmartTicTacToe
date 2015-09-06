package br.com.salomao.smarttictactoe.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.salomao.smarttictactoe.R;
import br.com.salomao.smarttictactoe.model.GameConfiguration;
import br.com.salomao.smarttictactoe.model.GameLevelEnum;
import br.com.salomao.smarttictactoe.model.GameStarterEnum;
import br.com.salomao.smarttictactoe.model.SymbolEnum;

public class GameConfigurationActivity extends AppCompatActivity {

    private Button levelEasyButton;
    private Button levelMediumButton;
    private Button levelHardButton;
    private Button symbolXButton;
    private Button symbolOButton;
    private Button gameStarterUserButton;
    private Button gameStarterComputerButton;
    private Button playButton;

    private GameConfiguration gameConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.resetScreenStateToDefault();
    }

    private void resetScreenStateToDefault() {
        this.gameConfiguration = new GameConfiguration();

        // Game Level

        this.chooseLevel(this.gameConfiguration.getGameLevel());

        this.levelEasyButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseLevel(GameLevelEnum.EASY);
            }
        });

        this.levelMediumButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseLevel(GameLevelEnum.MEDIUM);
            }
        });

        this.levelHardButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseLevel(GameLevelEnum.HARD);
            }
        });

        // User Symbol

        this.chooseUserSymbol(this.gameConfiguration.getUserSymbol());

        this.symbolXButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseUserSymbol(SymbolEnum.X);
            }
        });

        this.symbolOButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseUserSymbol(SymbolEnum.O);
            }
        });

        // Game Starter

        this.chooseGameStarter(this.gameConfiguration.getGameStarter());

        this.gameStarterUserButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseGameStarter(GameStarterEnum.USER);
            }
        });

        this.gameStarterComputerButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.chooseGameStarter(GameStarterEnum.COMPUTER);
            }
        });

        // Play

        this.playButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.startActivity(GameConfigurationActivity.this,
                        GameConfigurationActivity.this.gameConfiguration);
            }
        });
    }

    private void chooseLevel(GameLevelEnum gameLevel) {
        this.gameConfiguration.setGameLevel(gameLevel);

        // Reset buttons state
        this.resetLevelButtonDefault(this.levelEasyButton());
        this.resetLevelButtonDefault(this.levelMediumButton());
        this.resetLevelButtonDefault(this.levelHardButton());

        // Set the color for the selected button
        switch (gameLevel) {
            case MEDIUM:
                this.levelMediumButton().setBackgroundColor(this.getResources().getColor(R.color.light_yellow));
                this.levelMediumButton().setTextColor(this.getResources().getColor(R.color.dark_yellow));
                break;
            case HARD:
                this.levelHardButton().setBackgroundColor(this.getResources().getColor(R.color.light_red));
                this.levelHardButton().setTextColor(this.getResources().getColor(R.color.dark_red));
                break;
            case EASY:
            default:
                this.levelEasyButton().setBackgroundColor(this.getResources().getColor(R.color.light_green));
                this.levelEasyButton().setTextColor(this.getResources().getColor(R.color.dark_green));
        }
    }

    private void resetLevelButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }

    private void chooseUserSymbol(SymbolEnum symbol) {
        this.gameConfiguration.setUserSymbol(symbol);

        // Reset buttons state
        this.resetSymbolButtonDefault(this.symbolXButton());
        this.resetSymbolButtonDefault(this.symbolOButton());

        // Set the color for the selected button
        switch (symbol) {
            case O:
                this.symbolOButton().setBackgroundColor(this.getResources().getColor(R.color.light_red));
                this.symbolOButton().setTextColor(this.getResources().getColor(R.color.dark_red));
                break;
            case X:
            default:
                this.symbolXButton().setBackgroundColor(this.getResources().getColor(R.color.light_blue));
                this.symbolXButton().setTextColor(this.getResources().getColor(R.color.dark_blue));
        }
    }

    private void resetSymbolButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }

    private void chooseGameStarter(GameStarterEnum gameStarter) {
        this.gameConfiguration.setGameStarter(gameStarter);

        // Reset buttons state
        this.resetGameStarterButtonDefault(this.gameStarterUserButton());
        this.resetGameStarterButtonDefault(this.gameStarterComputerButton());

        // Set the color for the selected button
        switch (this.gameConfiguration.getGameStarter()) {
            case COMPUTER:
                this.gameStarterComputerButton().setBackgroundColor(this.getResources().getColor(R.color.light_blue_gray));
                this.gameStarterComputerButton().setTextColor(this.getResources().getColor(R.color.dark_blue_gray));
                break;
            case USER:
            default:
                this.gameStarterUserButton().setBackgroundColor(this.getResources().getColor(R.color.light_blue_gray));
                this.gameStarterUserButton().setTextColor(this.getResources().getColor(R.color.dark_blue_gray));
        }
    }

    private void resetGameStarterButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }


    /* View */

    private Button levelEasyButton() {
        if(this.levelEasyButton == null)
            this.levelEasyButton =
                    (Button) findViewById(R.id.game_configuration_level_easy_button);

        return this.levelEasyButton;
    }

    private Button levelMediumButton() {
        if(this.levelMediumButton == null)
            this.levelMediumButton =
                    (Button) findViewById(R.id.game_configuration_level_medium_button);

        return this.levelMediumButton;
    }

    private Button levelHardButton() {
        if(this.levelHardButton == null)
            this.levelHardButton =
                    (Button) findViewById(R.id.game_configuration_level_hard_button);

        return this.levelHardButton;
    }

    private Button symbolXButton() {
        if(this.symbolXButton == null)
            this.symbolXButton =
                    (Button) findViewById(R.id.game_configuration_user_symbol_x_button);

        return this.symbolXButton;
    }

    private Button symbolOButton() {
        if(this.symbolOButton == null)
            this.symbolOButton =
                    (Button) findViewById(R.id.game_configuration_user_symbol_o_button);

        return this.symbolOButton;
    }

    private Button gameStarterUserButton() {
        if(this.gameStarterUserButton == null)
            this.gameStarterUserButton =
                    (Button) findViewById(R.id.game_configuration_game_starter_user_button);

        return gameStarterUserButton;
    }

    private Button gameStarterComputerButton() {
        if(this.gameStarterComputerButton == null)
            this.gameStarterComputerButton =
                    (Button) findViewById(R.id.game_configuration_game_starter_computer_button);

        return gameStarterComputerButton;
    }

    private Button playButton() {
        if(this.playButton == null)
            this.playButton = (Button) findViewById(R.id.game_configuration_play_button);

        return playButton;
    }
}
