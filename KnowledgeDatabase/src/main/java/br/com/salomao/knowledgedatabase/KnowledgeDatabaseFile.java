package br.com.salomao.knowledgedatabase;

import java.util.List;

public class KnowledgeDatabaseFile {

    private List<Move> moves;

    public List<Move> getMoves() {
        return moves;
    }

    public class Move {
        private List<Integer> state;
        private List<Integer> nextMove;

        public List<Integer> getState() {
            return state;
        }

        public List<Integer> getNextMove() {
            return nextMove;
        }
    }
}
