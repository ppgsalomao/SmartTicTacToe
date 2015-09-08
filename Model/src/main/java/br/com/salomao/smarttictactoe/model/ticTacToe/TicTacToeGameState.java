package br.com.salomao.smarttictactoe.model.ticTacToe;

import br.com.salomao.smarttictactoe.model.GameMarkerEnum;
import br.com.salomao.smarttictactoe.model.GameResultEnum;
import br.com.salomao.smarttictactoe.model.GameState;
import br.com.salomao.smarttictactoe.model.Position;

public class TicTacToeGameState implements GameState {

    private static final int GAME_SLOTS = 9;

    private int state;

    public TicTacToeGameState() {
        super();
        this.state = 0x00; // State Empty
    }

    public GameResultEnum getResult() {
        // TODO: Validate game result.
        return GameResultEnum.COMPUTER_WON;
    }

    public GameMarkerEnum getPositionMarker(Position position) {
        int positionRepresentation = (position.getRow() * 3) + position.getColumn();

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
        int positionRepresentation = (position.getRow() * 3) + position.getColumn();

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

    private int getUserPositionHexValue(int position) {
        return (int) Math.pow(2, position);
    }

    private int getComputerPositionHexValue(int position) {
        return (int) Math.pow(2, position + GAME_SLOTS);
    }
}
