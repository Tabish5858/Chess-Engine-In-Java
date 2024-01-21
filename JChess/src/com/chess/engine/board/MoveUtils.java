package com.chess.engine.board;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.board.Move.*;
import static com.chess.engine.board.Move.MoveFactory.*;

public enum MoveUtils {

    INSTANCE;

    public static final Move NULL_MOVE = new NullMove();

    public static int exchangeScore(final Move move) {
        if(move == getNullMove()) {
            return 1;
        }
        return move.isAttack() ?
                5 * exchangeScore(move.getBoard().getTransitionMove()) :
                exchangeScore(move.getBoard().getTransitionMove());

    }

    public static class Line {
        private final List<Integer> coordinates;

        public Line() {
            this.coordinates = new ArrayList<>();
        }

        public void addCoordinate(int coordinate) {
            this.coordinates.add(coordinate);
        }

        public List<Integer> getLineCoordinates() {
            return this.coordinates;
        }

        public boolean isEmpty() {
            return this.coordinates.isEmpty();
        }
    }
}
