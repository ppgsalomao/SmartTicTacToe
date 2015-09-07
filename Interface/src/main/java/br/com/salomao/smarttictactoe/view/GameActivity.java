package br.com.salomao.smarttictactoe.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.salomao.smarttictactoe.R;
import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;
import br.com.salomao.smarttictactoe.model.configuration.SymbolEnum;
import br.com.salomao.smarttictactoe.view.component.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseActivity {

    private static final String GAME_CONFIGURATION_EXTRA_KEY = "GameConfiguration";

    private GameConfiguration gameConfiguration;

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

        Button button;

        button = ButterKnife.findById(this, R.id.game_1x1_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_2x1_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_3x1_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_1x2_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_2x2_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_3x2_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_1x3_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_2x3_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
        button = ButterKnife.findById(this, R.id.game_3x3_button);
        button.setText(this.convertMarkerToString(state.getPositionMarker(this.getButtonPosition(button))));
    }

    private String convertMarkerToString(GameMarkerEnum marker) {
        String computerMarker = "X";
        if(this.gameConfiguration.getUserSymbol().equals(SymbolEnum.X))
            computerMarker = "O";

        String userMarker = "X";
        if(this.gameConfiguration.getUserSymbol().equals(SymbolEnum.O))
            userMarker = "O";

        switch (marker) {
            case USER:
                return userMarker;
            case COMPUTER:
                return computerMarker;
            case NONE:
            default:
                return "";
        }
    }

    @OnClick({ R.id.game_1x1_button, R.id.game_2x1_button, R.id.game_3x1_button,
               R.id.game_1x2_button, R.id.game_2x2_button, R.id.game_3x2_button,
               R.id.game_1x3_button, R.id.game_2x3_button, R.id.game_3x3_button })
    public void gameButtonClicked(Button button) {

        Position buttonPosition = this.getButtonPosition(button);
        GameState newGameState = this.gameEngine.userMarkedPosition(buttonPosition);
        this.updateScreenForState(newGameState);

    }

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

}
