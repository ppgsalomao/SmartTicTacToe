package br.com.salomao.inferenceengine;

import java.util.List;

import javax.inject.Inject;

import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameResultBuilder;
import br.com.salomao.smarttictactoe.model.GameResultEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.ticTacToe.TicTacToeGameState;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;
import br.com.salomao.smarttictactoe.model.configuration.GameStarterEnum;

public class TicTacToeGameEngine implements GameEngine {

    private TicTacToeGameState state;

    @Inject
    public TicTacToeGameEngine() {
        this.startGame(new GameConfiguration());
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

            GameResultEnum result = this.state.getResult();
            List<Position> freePositions = this.state.getFreePositions();
            if(freePositions != null && freePositions.size() > 0 && result.equals(GameResultEnum.UNDEFINED)) {
                Position computerPosition = null;
                // TODO: mark computer play from Knowledge database.

                // If no position to play is found, choose one randomly.
                if(computerPosition == null) {
                    if(freePositions.size() > 0) {
                        int random = (int) (freePositions.size() * Math.random());
                        computerPosition = freePositions.get(random);
                    }
                }

                this.state.markPosition(computerPosition, GameMarkerEnum.COMPUTER);
            }
        }

        return this.state;
    }
}
