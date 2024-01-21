package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.chess.engine.Alliance.BLACK;
import static com.chess.engine.Alliance.WHITE;
import static com.chess.engine.board.Move.MoveFactory.getNullMove;

public final class Board {

    private static final Board STANDARD_BOARD = createStandardBoardImpl();
    private final Map<Integer, Piece> boardConfig;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private Board(final Builder builder) {
        this.boardConfig = Collections.unmodifiableMap(builder.boardConfig);
        this.whitePieces = calculateActivePieces(builder, WHITE);
        this.blackPieces = calculateActivePieces(builder, BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : getNullMove();
    }

    private static String prettyPrint(final Piece piece) {
        if (piece != null) {
            return piece.getPieceAllegiance().isBlack() ?
                    piece.toString().toLowerCase() : piece.toString();
        }
        return "-";
    }

    public static Board createStandardBoard() {
        return STANDARD_BOARD;
    }

    private static Board createStandardBoardImpl() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(BLACK, 0));
        builder.setPiece(new Knight(BLACK, 1));
        builder.setPiece(new Bishop(BLACK, 2));
        builder.setPiece(new Queen(BLACK, 3));
        builder.setPiece(new King(BLACK, 4, true, true));
        builder.setPiece(new Bishop(BLACK, 5));
        builder.setPiece(new Knight(BLACK, 6));
        builder.setPiece(new Rook(BLACK, 7));
        builder.setPiece(new Pawn(BLACK, 8));
        builder.setPiece(new Pawn(BLACK, 9));
        builder.setPiece(new Pawn(BLACK, 10));
        builder.setPiece(new Pawn(BLACK, 11));
        builder.setPiece(new Pawn(BLACK, 12));
        builder.setPiece(new Pawn(BLACK, 13));
        builder.setPiece(new Pawn(BLACK, 14));
        builder.setPiece(new Pawn(BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(WHITE, 48));
        builder.setPiece(new Pawn(WHITE, 49));
        builder.setPiece(new Pawn(WHITE, 50));
        builder.setPiece(new Pawn(WHITE, 51));
        builder.setPiece(new Pawn(WHITE, 52));
        builder.setPiece(new Pawn(WHITE, 53));
        builder.setPiece(new Pawn(WHITE, 54));
        builder.setPiece(new Pawn(WHITE, 55));
        builder.setPiece(new Rook(WHITE, 56));
        builder.setPiece(new Knight(WHITE, 57));
        builder.setPiece(new Bishop(WHITE, 58));
        builder.setPiece(new Queen(WHITE, 59));
        builder.setPiece(new King(WHITE, 60, true, true));
        builder.setPiece(new Bishop(WHITE, 61));
        builder.setPiece(new Knight(WHITE, 62));
        builder.setPiece(new Rook(WHITE, 63));
        //white to move
        builder.setMoveMaker(WHITE);
        //build the board
        return builder.build();
    }

    private static Collection<Piece> calculateActivePieces(final Builder builder,
                                                           final Alliance alliance) {
        return builder.boardConfig.values().stream()
                .filter(piece -> piece.getPieceAllegiance() == alliance)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = prettyPrint(this.boardConfig.get(i));
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(),
                this.blackPieces.stream()).collect(Collectors.toList());
    }

    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.whitePlayer.getLegalMoves().stream(),
                this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                .collect(Collectors.toList());
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setEnPassantPawn(final Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public Builder setMoveTransition(final Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
