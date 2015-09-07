package br.com.salomao.smarttictactoe.model;

import br.com.salomao.smarttictactoe.model.configuration.GameConfiguration;


public interface GameEngine {

    GameState startGame(GameConfiguration configuration);
    GameState userMarkedPosition(Position position);

}
