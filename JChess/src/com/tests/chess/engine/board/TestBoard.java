package com.tests.chess.engine.board;

import com.chess.engine.board.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    @Test
    public void initialBoard() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 11);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 19);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
//        assertTrue(board.currentPlayer().isKingSideCastleCapable());
//        assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());

//        assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
//        assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
//        assertTrue(board.whitePlayer().toString().equals("White"));
//        assertTrue(board.blackPlayer().toString().equals("Black"));
//        assertEquals(new StandardBoardEvaluator().evaluate(board,0),0);
    }
}