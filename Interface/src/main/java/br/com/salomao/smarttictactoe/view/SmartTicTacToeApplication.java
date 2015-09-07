package br.com.salomao.smarttictactoe.view;

import android.app.Application;

import javax.inject.Singleton;

import br.com.salomao.smarttictactoe.injection.AndroidModule;
import br.com.salomao.smarttictactoe.view.component.BaseActivity;
import dagger.Component;

public class SmartTicTacToeApplication extends Application {

    @Singleton
    @Component(modules = AndroidModule.class)
    public interface ApplicationComponent {
        void inject(SmartTicTacToeApplication application);
        void inject(BaseActivity activity);
        void inject(GameConfigurationActivity activity);
        void inject(GameActivity activity);
    }

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component =
                DaggerSmartTicTacToeApplication_ApplicationComponent
                    .builder()
                    .androidModule(new AndroidModule(this))
                    .build();
        component().inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }
}
