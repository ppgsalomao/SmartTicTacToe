package br.com.salomao.smarttictactoe.model;

import java.util.ArrayList;
import java.util.List;

public enum GameResultEnum {
    USER_WON, COMPUTER_WON, DRAW, UNDEFINED;

    private List<Position> winPositions;

    GameResultEnum() {
        this.winPositions = new ArrayList<>();
    }

    GameResultEnum(List<Position> winPositions) {
        this.winPositions = winPositions;
    }

    public List<Position> getWinPositions() {
        return winPositions;
    }

    public void setWinPositions(List<Position> winPositions) {
        this.winPositions = winPositions;
    }
}
