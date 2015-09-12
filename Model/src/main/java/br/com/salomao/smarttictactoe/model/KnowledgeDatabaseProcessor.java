package br.com.salomao.smarttictactoe.model;

public interface KnowledgeDatabaseProcessor {

    void loadKnowledgeDatabaseFromFile(int fileResourceId) throws Exception;
    Position calculateNextComputerMove(GameState state);

}
