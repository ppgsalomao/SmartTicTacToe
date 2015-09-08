package br.com.salomao.smarttictactoe.injection;

import br.com.salomao.smarttictactoe.view.GameActivity;
import dagger.Component;

@Component(
    modules = {
        GameModule.class
    }
)
public interface GameUIComponent {

    void inject(GameActivity activity);

}
