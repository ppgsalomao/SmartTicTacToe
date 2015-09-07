package br.com.salomao.inferenceengine;

import javax.inject.Inject;

import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.ticTacToe.TicTacToeGameState;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;
import br.com.salomao.smarttictactoe.model.configuration.GameStarterEnum;

public class TicTacToeGameEngine implements GameEngine {

    private TicTacToeGameState state;

    @Inject
    public TicTacToeGameEngine() {
    }

    public GameState startGame(GameConfiguration configuration) {

        this.state = new TicTacToeGameState();

        // TODO: Get From Knowlegde Database. First computer move on the center
        if(configuration.getGameStarter().equals(GameStarterEnum.COMPUTER)) {
            this.state.markPosition(new Position(1, 1), GameMarkerEnum.COMPUTER);
        }

        // TODO: Use game level to load Knowledge Database.

        return this.state;
    }

    public GameState userMarkedPosition(Position position) {
        if(position == null)
            throw new IllegalArgumentException("Invalid position. Should not be null.");
        if(position.getRow() >= 3)
            throw new IllegalArgumentException("Invalid row value. Max of 2.");
        if(position.getColumn() >= 3)
            throw new IllegalArgumentException("Invalid column value. Max of 2.");

        GameMarkerEnum marker = this.state.getPositionMarker(position);
        if(marker != null && marker.equals(GameMarkerEnum.NONE)) {
            this.state.markPosition(position, GameMarkerEnum.USER);
        }

        // TODO: mark computer play.

        return this.state;
    }
}
