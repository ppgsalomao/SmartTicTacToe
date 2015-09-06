package br.com.salomao.smarttictactoe.model;

public class GameState {

    private static final int GAME_SLOTS = 9;

    private int state;

    public GameState() {
        super();

        this.state = 0x00;
    }

    public GameMarkerEnum getPositionMarker(int position) {
        if(position >= GAME_SLOTS)
            throw new IllegalArgumentException("TicTacToe has only "+ GAME_SLOTS + " slots.");
        if(position < 0)
            throw new IllegalArgumentException("The position value must be positive.");

        int userPositionHexValue = this.getUserPositionHexValue(position);
        if((this.state & userPositionHexValue) == userPositionHexValue)
            return GameMarkerEnum.USER;

        int computerPositionHexValue = this.getComputerPositionHexValue(position);
        if((this.state & computerPositionHexValue) == computerPositionHexValue)
            return GameMarkerEnum.COMPUTER;

        return GameMarkerEnum.NONE;
    }

    public void markPosition(int position, GameMarkerEnum marker) {
        if(position >= GAME_SLOTS)
            throw new IllegalArgumentException("TicTacToe has only " + GAME_SLOTS + " slots.");
        if(position < 0)
            throw new IllegalArgumentException("The position value must be positive.");

        if(marker == null || marker.equals(GameMarkerEnum.NONE)) {
            state = state & ~this.getUserPositionHexValue(position);        // Remove User Mark
            state = state & ~this.getComputerPositionHexValue(position);    // Remove Computer Mark
        } else if(marker.equals(GameMarkerEnum.COMPUTER)) {
            state = state & ~this.getUserPositionHexValue(position);        // Remove User Mark
            state = (state | this.getComputerPositionHexValue(position));   // Insert Computer Mark
        } else if(marker.equals(GameMarkerEnum.USER)) {
            state = (state | this.getUserPositionHexValue(position));       // Insert User Mark
            state = state & ~this.getComputerPositionHexValue(position);    // Remove Computer Mark
        }
    }

    private int getUserPositionHexValue(int position) {
        return (int) Math.pow(2, position);
    }

    private int getComputerPositionHexValue(int position) {
        return (int) Math.pow(2, position + GAME_SLOTS);
    }
}
