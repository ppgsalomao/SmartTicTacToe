package br.com.salomao.knowledgedatabase;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.KnowledgeDatabaseProcessor;
import br.com.salomao.smarttictactoe.model.Position;
import br.com.salomao.smarttictactoe.model.ticTacToe.TicTacToeGameState;

public class TicTacToeKnowledgeDatabaseProcessor implements KnowledgeDatabaseProcessor {

    private Context context;
    private SparseArray<Position> movesKnwoledgeDatabase;

    public TicTacToeKnowledgeDatabaseProcessor(Context context) {
        this.context = context;

        this.movesKnwoledgeDatabase = new SparseArray<>();
    }

    private String readJSON(int rawId) throws Exception {
        InputStream is = this.context.getResources().openRawResource(rawId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }

    private GameState convertToGameState(List<Integer> knowledgeDatabaseFileState) {
        if(knowledgeDatabaseFileState != null) {
            TicTacToeGameState gameState = new TicTacToeGameState();

            if(knowledgeDatabaseFileState.size() > 0) {
                int i = 0;
                for (Integer marker : knowledgeDatabaseFileState) {
                    Position position = new Position(i % 3, i / 3);
                    if(marker == null)
                        gameState.markPosition(position, GameMarkerEnum.NONE);
                    else if(marker == 1)
                        gameState.markPosition(position, GameMarkerEnum.USER);
                    else if(marker == 2)
                        gameState.markPosition(position, GameMarkerEnum.COMPUTER);
                    else
                        gameState.markPosition(position, GameMarkerEnum.NONE);

                    i++;
                }
            }

            return gameState;
        }

        return null;
    }

    private Position convertToPosition(List<Integer> knowledgeDatabaseFilePosition) {
        if(knowledgeDatabaseFilePosition != null && knowledgeDatabaseFilePosition.size() >= 2)
            return new Position(knowledgeDatabaseFilePosition.get(0), knowledgeDatabaseFilePosition.get(1));

        return null;
    }

    /* KnowledgeDatabaseProcessor */

    @Override
    public void loadKnowledgeDatabaseFromFile(int fileResourceId) throws Exception {
        this.movesKnwoledgeDatabase = new SparseArray<>();

        String json = this.readJSON(fileResourceId);
        KnowledgeDatabaseFile knowledgeDatabaseFile = new Gson().fromJson(json, KnowledgeDatabaseFile.class);

        if(knowledgeDatabaseFile != null) {
            if(knowledgeDatabaseFile.getMoves() != null && knowledgeDatabaseFile.getMoves().size() > 0)
                for (KnowledgeDatabaseFile.Move move : knowledgeDatabaseFile.getMoves()) {
                    Position nextMove = this.convertToPosition(move.getNextMove());
                    if(nextMove != null) {
                        GameState state = this.convertToGameState(move.getState());
                        this.validateDatabaseState(state, nextMove);
                        this.movesKnwoledgeDatabase.append(state.getStateNumberRepresentation(), nextMove);

                        state = this.rotateClockwiseState(state);
                        nextMove = this.rotateClockwisePosition(nextMove);
                        this.validateDatabaseState(state, nextMove);
                        this.movesKnwoledgeDatabase.append(state.getStateNumberRepresentation(), nextMove);

                        state = this.rotateClockwiseState(state);
                        nextMove = this.rotateClockwisePosition(nextMove);
                        this.validateDatabaseState(state, nextMove);
                        this.movesKnwoledgeDatabase.append(state.getStateNumberRepresentation(), nextMove);

                        state = this.rotateClockwiseState(state);
                        nextMove = this.rotateClockwisePosition(nextMove);
                        this.validateDatabaseState(state, nextMove);
                        this.movesKnwoledgeDatabase.append(state.getStateNumberRepresentation(), nextMove);
                    }
                }
        }
    }

    public void validateDatabaseState(GameState state, Position nextMove) {
        if(state == null || nextMove == null)
            throw new IllegalStateException("State or position null for Knowledge Database");

        GameMarkerEnum marker = state.getPositionMarker(nextMove);
        if(marker == null || !marker.equals(GameMarkerEnum.NONE))
            throw new IllegalStateException("Next move on position already filled");
    }

    public GameState rotateClockwiseState(GameState state) {
        GameState rotatedState = new TicTacToeGameState();

        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                Position originalPosition = new Position(column, row);
                rotatedState.markPosition(
                        this.rotateClockwisePosition(originalPosition),
                        state.getPositionMarker(originalPosition));
            }
        }

        return rotatedState;
    }

    public Position rotateClockwisePosition(Position position) {
        if(position == null)
            return null;

        int column = position.getRow();
        if(column != 1)
            column = column ^ 0x2;

        return new Position(column, position.getColumn());
    }

    @Override
    public Position calculateNextComputerMove(GameState state) {
        return this.movesKnwoledgeDatabase.get(state.getStateNumberRepresentation());
    }
}
