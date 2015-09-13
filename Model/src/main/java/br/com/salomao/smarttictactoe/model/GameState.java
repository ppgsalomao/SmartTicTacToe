package br.com.salomao.smarttictactoe.model;

import java.util.List;

public interface GameState {

    GameResultEnum getResult();
    GameMarkerEnum getPositionMarker(Position position);
    void markPosition(Position position, GameMarkerEnum marker);
    List<Position> getFreePositions();

    int getStateNumberRepresentation();

    String toPrettyString();
}
