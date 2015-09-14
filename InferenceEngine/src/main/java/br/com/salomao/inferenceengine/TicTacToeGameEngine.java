package br.com.salomao.inferenceengine;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import br.com.salomao.knowledgedatabase.TicTacToeKnowledgeDatabaseProcessor;
import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameResultEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.KnowledgeDatabaseProcessor;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;
import br.com.salomao.smarttictactoe.model.configuration.GameStarterEnum;
import br.com.salomao.smarttictactoe.model.ticTacToe.TicTacToeGameState;

public class TicTacToeGameEngine implements GameEngine {

    private KnowledgeDatabaseProcessor knowledgeDatabaseProcessor;
    private TicTacToeGameState state;

    @Inject
    public TicTacToeGameEngine(Context context) {
        this.knowledgeDatabaseProcessor = new TicTacToeKnowledgeDatabaseProcessor(context);
        this.startGame(new GameConfiguration());
    }

    @Override
    public GameState startGame(GameConfiguration configuration) {
        // Create state of beginning.
        this.state = new TicTacToeGameState();

        try {
            // Initialize Knowledge Database, based on game configuration.
            switch (configuration.getGameLevel()) {
                case HARD:
                    if (configuration.getGameStarter().equals(GameStarterEnum.COMPUTER))
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.computer_first_player_hard);
                    else
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.user_first_player_hard);
                    break;
                case MEDIUM:
                    if (configuration.getGameStarter().equals(GameStarterEnum.COMPUTER))
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.computer_first_player_medium);
                    else
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.user_first_player_medium);
                    break;
                case EASY:
                default:
                    if (configuration.getGameStarter().equals(GameStarterEnum.COMPUTER))
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.computer_first_player_easy);
                    else
                        this.knowledgeDatabaseProcessor.loadKnowledgeDatabaseFromFile(R.raw.user_first_player_easy);
                    break;
            }
        } catch(Exception e) {
            Log.e("GameEngine", "Failed to load Knowledge Database file.", e);
        }

        if(configuration.getGameStarter().equals(GameStarterEnum.COMPUTER)) {
            Position nextComputerMove = this.getNextComputerMove();
            if(nextComputerMove != null)
                this.state.markPosition(nextComputerMove, GameMarkerEnum.COMPUTER);
        }

        return this.state;
    }

    @Override
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
            Position computerNextMove = this.getNextComputerMove();
            if(result.equals(GameResultEnum.UNDEFINED) && computerNextMove != null) {
                this.state.markPosition(computerNextMove, GameMarkerEnum.COMPUTER);
            }
        }

        return this.state;
    }

    private Position getNextComputerMove() {
        Position computerPosition = this.knowledgeDatabaseProcessor.calculateNextComputerMove(this.state);

        // If no position to play is found, choose one randomly.
        if(computerPosition == null) {
            List<Position> freePositions = this.state.getFreePositions();
            if(freePositions.size() > 0) {
                int random = (int) (freePositions.size() * Math.random());
                computerPosition = freePositions.get(random);
            }
        }

        return computerPosition;
    }
}
