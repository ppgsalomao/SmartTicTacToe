package br.com.salomao.smarttictactoe.injection;

import android.content.Context;

import br.com.salomao.inferenceengine.TicTacToeGameEngine;
import br.com.salomao.smarttictactoe.model.GameEngine;
import br.com.salomao.smarttictactoe.model.KnowledgeDatabaseProcessor;
import dagger.Module;
import dagger.Provides;

@Module
public class GameModule {

    private Context context;

    public GameModule(Context context) {
        this.context = context;
    }

    @Provides
    GameEngine provideGameEngine() {
        return new TicTacToeGameEngine(this.context);
    }
}
