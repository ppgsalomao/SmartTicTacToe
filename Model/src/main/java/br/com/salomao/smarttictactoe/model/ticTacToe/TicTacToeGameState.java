package br.com.salomao.smarttictactoe.model.ticTacToe;

import java.util.ArrayList;
import java.util.List;

import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameResultBuilder;
import br.com.salomao.smarttictactoe.model.GameResultEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.Position;

public class TicTacToeGameState implements GameState {

    private static final int GAME_SLOTS = 9;
    private static final int COLUMS_PER_ROW = 3;

    private int state;

    public TicTacToeGameState() {
        super();
        this.state = 0x00; // State Empty
    }

    private int getUserPositionHexValue(int position) {
        return (int) Math.pow(2, position);
    }

    private int getComputerPositionHexValue(int position) {
        return (int) Math.pow(2, position + GAME_SLOTS);
    }

    private GameResultEnum getResultForPositions(int[][] positionIndexes) {

        GameResultEnum result = null;
        GameMarkerEnum winner = null;
        List<Position> positions = new ArrayList<>();

        for (int[] positionIndex : positionIndexes) {
            Position position = new Position(positionIndex[0], positionIndex[1]);
            positions.add(position);
            GameMarkerEnum marker = this.getPositionMarker(position);

            if (winner == null)
                winner = marker;

            else if(!winner.equals(marker))

                if (marker.equals(GameMarkerEnum.NONE))
                    winner = GameMarkerEnum.NONE;
                else
                    result = GameResultEnum.DRAW;
        }

        if(winner == null || winner.equals(GameMarkerEnum.NONE))
            return GameResultEnum.UNDEFINED;

        if(result == null || !result.equals(GameResultEnum.DRAW)) {
            if(winner.equals(GameMarkerEnum.USER)) {
                result = GameResultEnum.USER_WON;
                result.setWinPositions(positions);
            } else if(winner.equals(GameMarkerEnum.COMPUTER)) {
                result = GameResultEnum.COMPUTER_WON;
                result.setWinPositions(positions);
            }
        }

        return result;
    }

    /* GameState */

    // TODO: Cache result
    public GameResultEnum getResult() {

        int[][][] winPositions = {
                { { 0, 0 }, { 1, 0 }, { 2, 0 } }, // First Line
                { { 0, 1 }, { 1, 1 }, { 2, 1 } }, // Second Line
                { { 0, 2 }, { 1, 2 }, { 2, 2 } }, // Third Line

                { { 0, 0 }, { 0, 1 }, { 0, 2 } }, // First Colum
                { { 1, 0 }, { 1, 1 }, { 1, 2 } }, // Second Colum
                { { 2, 0 }, { 2, 1 }, { 2, 2 } }, // Third Colum

                { { 0, 0 }, { 1, 1 }, { 2, 2 } }, // Descending diagonal
                { { 0, 2 }, { 1, 1 }, { 2, 0 } }, // Ascending diagonal
        };

        for (int[][] positionsIndexes : winPositions) {
            GameResultEnum result = this.getResultForPositions(positionsIndexes);
            if(result.equals(GameResultEnum.USER_WON) || result.equals(GameResultEnum.COMPUTER_WON))
                return result;
        }

        if(this.getFreePositions().size() > 0)
            return GameResultEnum.UNDEFINED;
        else
            return GameResultEnum.DRAW;
    }

    public GameMarkerEnum getPositionMarker(Position position) {
        int positionRepresentation = (position.getRow() * COLUMS_PER_ROW) + position.getColumn();

        if(positionRepresentation >= GAME_SLOTS)
            throw new IllegalArgumentException("TicTacToe has only "+ GAME_SLOTS + " slots.");
        if(positionRepresentation < 0)
            throw new IllegalArgumentException("The position value must be positive.");

        int userPositionHexValue = this.getUserPositionHexValue(positionRepresentation);
        if((this.state & userPositionHexValue) == userPositionHexValue)
            return GameMarkerEnum.USER;

        int computerPositionHexValue = this.getComputerPositionHexValue(positionRepresentation);
        if((this.state & computerPositionHexValue) == computerPositionHexValue)
            return GameMarkerEnum.COMPUTER;

        return GameMarkerEnum.NONE;
    }

    public void markPosition(Position position, GameMarkerEnum marker) {
        int positionRepresentation = (position.getRow() * COLUMS_PER_ROW) + position.getColumn();

        if(positionRepresentation >= GAME_SLOTS)
            throw new IllegalArgumentException("TicTacToe has only " + GAME_SLOTS + " slots.");
        if(positionRepresentation < 0)
            throw new IllegalArgumentException("The position value must be positive.");

        if(marker == null || marker.equals(GameMarkerEnum.NONE)) {
            state = state & ~this.getUserPositionHexValue(positionRepresentation);        // Remove User Mark
            state = state & ~this.getComputerPositionHexValue(positionRepresentation);    // Remove Computer Mark
        } else if(marker.equals(GameMarkerEnum.COMPUTER)) {
            state = state & ~this.getUserPositionHexValue(positionRepresentation);        // Remove User Mark
            state = (state | this.getComputerPositionHexValue(positionRepresentation));   // Insert Computer Mark
        } else if(marker.equals(GameMarkerEnum.USER)) {
            state = (state | this.getUserPositionHexValue(positionRepresentation));       // Insert User Mark
            state = state & ~this.getComputerPositionHexValue(positionRepresentation);    // Remove Computer Mark
        }
    }

    @Override
    public List<Position> getFreePositions() {
        List<Position> freePositions = new ArrayList<>();
        for (int i = 0; i < GAME_SLOTS; i++) {
            int row = i / COLUMS_PER_ROW;
            int column = i % COLUMS_PER_ROW;
            Position position = new Position(column, row);

            if(this.getPositionMarker(position).equals(GameMarkerEnum.NONE))
                freePositions.add(position);
        }
        return freePositions;
    }

    @Override
    public int getStateNumberRepresentation() {
        return this.state;
    }

    @Override
    public String toPrettyString() {
        StringBuilder prettyString = new StringBuilder();

        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(0, 0))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(1, 0))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(2, 0))) + " \n");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(0, 1))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(1, 1))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(2, 1))) + " \n");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(0, 2))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(1, 2))) + " |");
        prettyString.append(" " + this.getMarkerString(this.getPositionMarker(new Position(2, 2))) + " ");

        return prettyString.toString();
    }

    private String getMarkerString(GameMarkerEnum marker) {
        if(marker == null)
            return " ";

        switch (marker) {
            case USER:
                return "U";
            case COMPUTER:
                return "C";
            case NONE:
            default:
                return " ";
        }
    }
}
