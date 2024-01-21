package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;

import java.util.*;

import static com.chess.engine.board.BoardUtils.*;
import static com.chess.engine.board.BoardUtils.INSTANCE;
import static com.chess.engine.board.MoveUtils.*;
import static com.chess.engine.pieces.Piece.PieceType.BISHOP;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    private final static Map<Integer, Line[]> PRECOMPUTED_CANDIDATES = computeCandidates();


    public Bishop(final Alliance alliance,
                  final int piecePosition) {
        super(BISHOP, alliance, piecePosition, true);
    }

    public Bishop(final Alliance alliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(BISHOP, alliance, piecePosition, isFirstMove);
    }

    private static Map<Integer, Line[]> computeCandidates() {
        Map<Integer, Line[]> candidates = new HashMap<>();
        for (int position = 0; position < NUM_TILES; position++) {
            List<Line> lines = new ArrayList<>();
            for (int offset : CANDIDATE_MOVE_COORDINATES) {
                int destination = position;
                Line line = new Line();
                while (isValidTileCoordinate(destination)) {
                    if (isFirstColumnExclusion(destination, offset) ||
                            isEighthColumnExclusion(destination, offset)) {
                        break;
                    }
                    destination += offset;
                    if (isValidTileCoordinate(destination)) {
                        line.addCoordinate(destination);
                    }
                }
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            if (!lines.isEmpty()) {
                candidates.put(position, lines.toArray(new Line[0]));
            }
        }
        return Collections.unmodifiableMap(candidates);
    }
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Line line : PRECOMPUTED_CANDIDATES.get(this.piecePosition)) {
            for (final int candidateDestinationCoordinate : line.getLineCoordinates()) {
                final Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAllegiance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                    break;
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }
    @Override
    public Bishop movePiece(final Move move) {
        return PieceUtils.INSTANCE.getMovedBishop(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion(final int position,
                                                  final int offset) {
        return (INSTANCE.FIRST_COLUMN.get(position) &&
                ((offset == -9) || (offset == 7)));
    }

    private static boolean isEighthColumnExclusion(final int position,
                                                   final int offset) {
        return INSTANCE.EIGHTH_COLUMN.get(position) &&
                ((offset == -7) || (offset == 9));
    }

}