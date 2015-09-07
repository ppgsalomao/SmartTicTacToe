package br.com.salomao.smarttictactoe.model;

public interface GameState {

    GameResultEnum getResult();
    GameMarkerEnum getPositionMarker(Position position);
    void markPosition(Position position, GameMarkerEnum marker);

}
