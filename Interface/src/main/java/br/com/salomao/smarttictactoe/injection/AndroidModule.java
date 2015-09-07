package br.com.salomao.smarttictactoe.injection;

import br.com.salomao.inferenceengine.TicTacToeGameEngine;
import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.view.SmartTicTacToeApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
    private final SmartTicTacToeApplication application;

    public AndroidModule(SmartTicTacToeApplication application) {
        this.application = application;
    }

    @Provides
    GameEngine provideGameEngine() {
        return new TicTacToeGameEngine();
    }
}
