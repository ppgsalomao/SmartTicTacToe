package br.com.salomao.smarttictactoe.injection;

import android.content.Context;

import br.com.salomao.smarttictactoe.view.GameActivity;
import dagger.Component;

@Component(
    modules = {
        GameModule.class
    }
)
public interface GameUIComponent {

    void inject(GameActivity activity);

    class Initializer {
        public static GameUIComponent init(Context context) {
            return DaggerGameUIComponent.builder()
                    .gameModule(new GameModule(context))
                    .build();
        }
    }

}
