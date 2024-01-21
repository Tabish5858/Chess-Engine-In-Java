package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.chess.engine.board.Move.MoveStatus.*;
import static com.chess.engine.pieces.Piece.PieceType.KING;
import static java.util.stream.Collectors.collectingAndThen;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    protected final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> playerLegals,
           final Collection<Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegals).isEmpty();
        playerLegals.addAll(calculateKingCastles(playerLegals, opponentLegals));
        this.legalMoves = Collections.unmodifiableCollection(playerLegals);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && hasNoEscapeMoves();
    }
//    public boolean isInCheckMate() {
//        return this.isInCheck && this.playerKing == null  && !hasEscapeMoves();
//    }

    public boolean isInStaleMate() {
        return !this.isInCheck && hasNoEscapeMoves();
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    private King establishKing() {
        return (King) getActivePieces().stream()
                .filter(piece -> piece.getPieceType() == KING)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
//    private King establishKing() {
//        return (King) getActivePieces().stream()
//                .filter(piece -> piece.getPieceType() == KING)
//                .findAny()
//                .orElse(null);
//    }

    private boolean hasNoEscapeMoves() {
        return this.legalMoves.stream()
                .noneMatch(move -> makeMove(move)
                        .getMoveStatus().isDone());
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    static Collection<Move> calculateAttacksOnTile(final int tile,
                                                   final Collection<Move> moves) {
        return moves.stream()
                .filter(move -> move.getDestinationCoordinate() == tile)
                .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
    public MoveTransition makeMove(final Move move) {
        if (!this.legalMoves.contains(move)) {
            return new MoveTransition(this.board, this.board, move, ILLEGAL_MOVE);
        }
        final Board transitionedBoard = move.execute();
        return transitionedBoard.currentPlayer().getOpponent().isInCheck() ?
                new MoveTransition(this.board, this.board, move, LEAVES_PLAYER_IN_CHECK) :
                new MoveTransition(this.board, transitionedBoard, move, DONE);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,
                                                             Collection<Move> opponentLegals);
    protected boolean hasCastleOpportunities() {
        return !this.isInCheck && !this.playerKing.isCastled() &&
                (this.playerKing.isKingSideCastleCapable() || this.playerKing.isQueenSideCastleCapable());
    }

}
