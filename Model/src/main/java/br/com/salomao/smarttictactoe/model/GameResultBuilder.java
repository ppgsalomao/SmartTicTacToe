package br.com.salomao.smarttictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class GameResultBuilder {

    private List<Position> positions;
    private GameResultEnum resultEnum;

    public GameResultBuilder() {
        this.positions = new ArrayList<>();
    }

    public GameResultBuilder setResult(GameResultEnum resultEnum) {
        this.resultEnum = resultEnum;
        return this;
    }

    public GameResultBuilder addPosition(Position position) {
        return this;
    }

    public GameResultEnum build() {
        if(this.resultEnum == null)
            this.resultEnum = GameResultEnum.UNDEFINED;

        this.resultEnum.setWinPositions(this.positions);

        return this.resultEnum;
    }
}
