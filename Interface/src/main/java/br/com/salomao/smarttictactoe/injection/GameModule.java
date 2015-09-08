package br.com.salomao.smarttictactoe.injection;

import br.com.salomao.inferenceengine.TicTacToeGameEngine;
import br.com.salomao.smarttictactoe.model.GameEngine;
import dagger.Module;
import dagger.Provides;

@Module
public class GameModule {

    public GameModule() {
    }

    @Provides
    GameEngine provideGameEngine() {
        return new TicTacToeGameEngine();
    }
}
