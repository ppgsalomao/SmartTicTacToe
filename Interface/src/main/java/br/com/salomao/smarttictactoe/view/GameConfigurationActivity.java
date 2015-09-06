package br.com.salomao.smarttictactoe.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import br.com.salomao.smarttictactoe.R;
import br.com.salomao.smarttictactoe.model.GameConfiguration;
import br.com.salomao.smarttictactoe.model.GameLevelEnum;
import br.com.salomao.smarttictactoe.model.GameStarterEnum;
import br.com.salomao.smarttictactoe.model.SymbolEnum;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameConfigurationActivity extends AppCompatActivity {

    @Bind(R.id.game_configuration_level_easy_button)
    Button levelEasyButton;
    @Bind(R.id.game_configuration_level_medium_button)
    Button levelMediumButton;
    @Bind(R.id.game_configuration_level_hard_button)
    Button levelHardButton;
    @Bind(R.id.game_configuration_user_symbol_x_button)
    Button symbolXButton;
    @Bind(R.id.game_configuration_user_symbol_o_button)
    Button symbolOButton;
    @Bind(R.id.game_configuration_game_starter_user_button)
    Button gameStarterUserButton;
    @Bind(R.id.game_configuration_game_starter_computer_button)
    Button gameStarterComputerButton;

    private GameConfiguration gameConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.resetScreenStateToDefault();
    }

    private void resetScreenStateToDefault() {
        this.gameConfiguration = new GameConfiguration();

        this.chooseLevel(this.gameConfiguration.getGameLevel());
        this.chooseUserSymbol(this.gameConfiguration.getUserSymbol());
        this.chooseGameStarter(this.gameConfiguration.getGameStarter());
    }

    private void chooseLevel(GameLevelEnum gameLevel) {
        this.gameConfiguration.setGameLevel(gameLevel);

        // Reset buttons state
        this.resetLevelButtonDefault(this.levelEasyButton);
        this.resetLevelButtonDefault(this.levelMediumButton);
        this.resetLevelButtonDefault(this.levelHardButton);

        // Set the color for the selected button
        switch (gameLevel) {
            case MEDIUM:
                this.levelMediumButton.setBackgroundColor(this.getResources().getColor(R.color.light_yellow));
                this.levelMediumButton.setTextColor(this.getResources().getColor(R.color.dark_yellow));
                break;
            case HARD:
                this.levelHardButton.setBackgroundColor(this.getResources().getColor(R.color.light_red));
                this.levelHardButton.setTextColor(this.getResources().getColor(R.color.dark_red));
                break;
            case EASY:
            default:
                this.levelEasyButton.setBackgroundColor(this.getResources().getColor(R.color.light_green));
                this.levelEasyButton.setTextColor(this.getResources().getColor(R.color.dark_green));
        }
    }

    private void resetLevelButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }

    private void chooseUserSymbol(SymbolEnum symbol) {
        this.gameConfiguration.setUserSymbol(symbol);

        // Reset buttons state
        this.resetSymbolButtonDefault(this.symbolXButton);
        this.resetSymbolButtonDefault(this.symbolOButton);

        // Set the color for the selected button
        switch (symbol) {
            case O:
                this.symbolOButton.setBackgroundColor(this.getResources().getColor(R.color.light_red));
                this.symbolOButton.setTextColor(this.getResources().getColor(R.color.dark_red));
                break;
            case X:
            default:
                this.symbolXButton.setBackgroundColor(this.getResources().getColor(R.color.light_blue));
                this.symbolXButton.setTextColor(this.getResources().getColor(R.color.dark_blue));
        }
    }

    private void resetSymbolButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }

    private void chooseGameStarter(GameStarterEnum gameStarter) {
        this.gameConfiguration.setGameStarter(gameStarter);

        // Reset buttons state
        this.resetGameStarterButtonDefault(this.gameStarterUserButton);
        this.resetGameStarterButtonDefault(this.gameStarterComputerButton);

        // Set the color for the selected button
        switch (this.gameConfiguration.getGameStarter()) {
            case COMPUTER:
                this.gameStarterComputerButton.setBackgroundColor(this.getResources().getColor(R.color.light_blue_gray));
                this.gameStarterComputerButton.setTextColor(this.getResources().getColor(R.color.dark_blue_gray));
                break;
            case USER:
            default:
                this.gameStarterUserButton.setBackgroundColor(this.getResources().getColor(R.color.light_blue_gray));
                this.gameStarterUserButton.setTextColor(this.getResources().getColor(R.color.dark_blue_gray));
        }
    }

    private void resetGameStarterButtonDefault(Button button) {
        button.setBackgroundColor(this.getResources().getColor(R.color.light_gray));
        button.setTextColor(this.getResources().getColor(R.color.dark_gray));
    }

    @OnClick({ R.id.game_configuration_level_easy_button,
            R.id.game_configuration_level_medium_button,
            R.id.game_configuration_level_hard_button  })
    public void onLevelButtonClicked(Button button) {

        switch (button.getId()) {
            case R.id.game_configuration_level_easy_button:
                this.chooseLevel(GameLevelEnum.EASY);
                break;
            case R.id.game_configuration_level_medium_button:
                this.chooseLevel(GameLevelEnum.MEDIUM);
                break;
            case R.id.game_configuration_level_hard_button:
                this.chooseLevel(GameLevelEnum.HARD);
                break;
            default:
        }
    }

    @OnClick({ R.id.game_configuration_user_symbol_x_button,
            R.id.game_configuration_user_symbol_o_button })
    public void onSymbolButtonClicked(Button button) {

        switch (button.getId()) {
            case R.id.game_configuration_user_symbol_x_button:
                GameConfigurationActivity.this.chooseUserSymbol(SymbolEnum.X);
                break;
            case R.id.game_configuration_user_symbol_o_button:
                GameConfigurationActivity.this.chooseUserSymbol(SymbolEnum.O);
                break;
            default:
        }
    }

    @OnClick({ R.id.game_configuration_game_starter_user_button,
            R.id.game_configuration_game_starter_computer_button })
    public void onGameStarterButtonClicked(Button button) {

        switch (button.getId()) {
            case R.id.game_configuration_game_starter_user_button:
                GameConfigurationActivity.this.chooseGameStarter(GameStarterEnum.USER);
                break;
            case R.id.game_configuration_game_starter_computer_button:
                GameConfigurationActivity.this.chooseGameStarter(GameStarterEnum.COMPUTER);
                break;
            default:
        }
    }

    @OnClick(R.id.game_configuration_play_button)
    public void onPlayButtonClicked() {
        GameActivity.startActivity(this, this.gameConfiguration);
    }
}
