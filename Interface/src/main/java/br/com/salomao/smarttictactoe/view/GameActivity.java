package br.com.salomao.smarttictactoe.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.salomao.smarttictactoe.R;
import br.com.salomao.smarttictactoe.injection.DaggerGameUIComponent;
import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameResultEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;
import br.com.salomao.smarttictactoe.model.configuration.SymbolEnum;
import br.com.salomao.smarttictactoe.view.component.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseActivity {

    private static final String GAME_CONFIGURATION_EXTRA_KEY = "GameConfiguration";

    private GameConfiguration gameConfiguration;
    @Bind({ R.id.game_1x1_button, R.id.game_2x1_button, R.id.game_3x1_button,
            R.id.game_1x2_button, R.id.game_2x2_button, R.id.game_3x2_button,
            R.id.game_1x3_button, R.id.game_2x3_button, R.id.game_3x3_button })
    List<Button> gameButtons;

    @Bind(R.id.game_result_text_view)
    TextView resultTextView;

    @Inject
    GameEngine gameEngine;

    public static void startActivity(Context context, GameConfiguration gameConfiguration) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(GAME_CONFIGURATION_EXTRA_KEY, gameConfiguration);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Injection
        DaggerGameUIComponent.Initializer.init(this).inject(this);
        ButterKnife.bind(this);

        Serializable gameConfigurationSerializableExtra =
                this.getIntent().getSerializableExtra(GAME_CONFIGURATION_EXTRA_KEY);
        if(gameConfigurationSerializableExtra != null
                && gameConfigurationSerializableExtra instanceof GameConfiguration) {
            this.gameConfiguration = (GameConfiguration) gameConfigurationSerializableExtra;
        }

        GameState initialGameState = this.gameEngine.startGame(this.gameConfiguration);
        this.updateScreenForState(initialGameState);
    }

    private void updateScreenForState(GameState state) {
        ButterKnife.apply(this.gameButtons, BUTTON_MARKER_SETTER, state);
        GameResultEnum result = state.getResult();

        this.fillWinPositionsBackground(result);
        this.updateResultTextView(result);
    }

    ButterKnife.Setter<Button, GameState> BUTTON_MARKER_SETTER =
            new ButterKnife.Setter<Button, GameState>() {
                @Override
                public void set(Button button, GameState state, int index) {
                    Position buttonPosition = GameActivity.this.getButtonPosition(button);
                    GameMarkerEnum buttonMarker = state.getPositionMarker(buttonPosition);
                    GameActivity.this.configureButtonText(button, buttonMarker);
                }
            };

    ButterKnife.Setter<Button, Integer> BUTTON_BACKGROUND_COLOR_SETTER =
            new ButterKnife.Setter<Button, Integer>() {
                @Override
                public void set(Button button, Integer color, int index) {
                    button.setBackgroundColor(color);
                }
            };

    @OnClick({ R.id.game_1x1_button, R.id.game_2x1_button, R.id.game_3x1_button,
               R.id.game_1x2_button, R.id.game_2x2_button, R.id.game_3x2_button,
               R.id.game_1x3_button, R.id.game_2x3_button, R.id.game_3x3_button })
    public void onGameButtonClicked(Button button) {

        Position buttonPosition = this.getButtonPosition(button);
        GameState newGameState = this.gameEngine.userMarkedPosition(buttonPosition);
        this.updateScreenForState(newGameState);

    }


    private void configureButtonText(Button button, GameMarkerEnum marker) {
        SymbolEnum symbol = this.getMarkerSymbol(marker);

        if(symbol != null) {
            switch (symbol) {
                case X:
                    button.setText("X");
                    button.setTextColor(this.getCompatColor(R.color.dark_blue));
                    break;
                case O:
                    button.setText("O");
                    button.setTextColor(this.getCompatColor(R.color.dark_red));
                    break;
                default:
                    button.setText("");
            }
        } else {
            button.setText("");
        }
    }

    private SymbolEnum getMarkerSymbol(GameMarkerEnum marker) {
        switch (marker) {
            case USER:
                return this.gameConfiguration.getUserSymbol();
            case COMPUTER:
                return (this.gameConfiguration.getUserSymbol().equals(SymbolEnum.O))
                        ?(SymbolEnum.X)
                        :(SymbolEnum.O);
            case NONE:
            default:
                return null;
        }
    }

    private void updateResultTextView(GameResultEnum result) {
        if(result != null)
            if(result.equals(GameResultEnum.USER_WON)) {

                int resultTextColor = (this.gameConfiguration.getUserSymbol().equals(SymbolEnum.X))
                        ? (R.color.dark_blue)
                        : (R.color.dark_red);
                this.resultTextView.setText(this.getString(R.string.game_result_userWon));
                this.resultTextView.setTextColor(this.getCompatColor(resultTextColor));

            } else if(result.equals(GameResultEnum.COMPUTER_WON)) {

                int resultTextColor = (this.gameConfiguration.getUserSymbol().equals(SymbolEnum.X))
                        ? (R.color.dark_red)
                        : (R.color.dark_blue);
                this.resultTextView.setText(this.getString(R.string.game_result_computerWon));
                this.resultTextView.setTextColor(this.getCompatColor(resultTextColor));

            } else if(result.equals(GameResultEnum.DRAW)) {

                this.resultTextView.setText(this.getString(R.string.game_result_draw));
                this.resultTextView.setTextColor(this.getCompatColor(R.color.black));

            } else {

                this.resultTextView.setText(" ");

            }
    }

    private void fillWinPositionsBackground(GameResultEnum result) {
        if(result != null) {

            int buttonBackgroundColor = R.color.white;
            if(result.equals(GameResultEnum.USER_WON))
                buttonBackgroundColor = (this.gameConfiguration.getUserSymbol().equals(SymbolEnum.X))
                        ?(R.color.light_blue)
                        :(R.color.light_red);
            else if(result.equals(GameResultEnum.COMPUTER_WON))
                buttonBackgroundColor = (this.gameConfiguration.getUserSymbol().equals(SymbolEnum.X))
                        ?(R.color.light_red)
                        :(R.color.light_blue);

            if(result.getWinPositions() != null && result.getWinPositions().size() > 0)
                for (Position position : result.getWinPositions()) {
                    Button button = this.getButtonForPosition(position);
                    button.setBackgroundColor(this.getCompatColor(buttonBackgroundColor));
                }

        }
    }

    /* Button finding */

    private Position getButtonPosition(Button button) {
        switch (button.getId()) {
            case R.id.game_1x1_button:
                return new Position(0, 0);
            case R.id.game_2x1_button:
                return new Position(1, 0);
            case R.id.game_3x1_button:
                return new Position(2, 0);
            case R.id.game_1x2_button:
                return new Position(0, 1);
            case R.id.game_2x2_button:
                return new Position(1, 1);
            case R.id.game_3x2_button:
                return new Position(2, 1);
            case R.id.game_1x3_button:
                return new Position(0, 2);
            case R.id.game_2x3_button:
                return new Position(1, 2);
            case R.id.game_3x3_button:
                return new Position(2, 2);
            default:
                return null;
        }
    }

    private Button getButtonForPosition(Position position) {
        int id = position.getColumn() + (position.getRow() * 3);
        return this.gameButtons.get(id);
    }
}
