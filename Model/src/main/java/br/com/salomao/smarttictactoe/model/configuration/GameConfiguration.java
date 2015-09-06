package br.com.salomao.smarttictactoe.model.configuration;

import java.io.Serializable;

public class GameConfiguration implements Serializable {
    private GameLevelEnum gameLevel;
    private SymbolEnum userSymbol;
    private GameStarterEnum gameStarter;

    public GameConfiguration() {
        super();

        this.gameLevel = GameLevelEnum.EASY;
        this.userSymbol = SymbolEnum.X;
        this.gameStarter = GameStarterEnum.USER;
    }

    public GameLevelEnum getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(GameLevelEnum gameLevel) {
        this.gameLevel = gameLevel;
    }

    public SymbolEnum getUserSymbol() {
        return userSymbol;
    }

    public void setUserSymbol(SymbolEnum userSymbol) {
        this.userSymbol = userSymbol;
    }

    public GameStarterEnum getGameStarter() {
        return gameStarter;
    }

    public void setGameStarter(GameStarterEnum gameStarter) {
        this.gameStarter = gameStarter;
    }
}
