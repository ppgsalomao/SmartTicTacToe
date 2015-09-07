package br.com.salomao.smarttictactoe.view;

import br.com.salomao.inferenceengine.TicTacToeGameEngine;
import br.com.salomao.smarttictactoe.model.GameEngine;
import dagger.Module;
import dagger.Provides;

@Module
public class GameEngineModule {

    @Provides
    GameEngine provideGameEngine(TicTacToeGameEngine ticTacToeGameEngine) {
        return ticTacToeGameEngine;
    }
}
