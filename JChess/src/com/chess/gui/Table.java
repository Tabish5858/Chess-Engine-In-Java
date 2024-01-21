package com.chess.gui;

import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.LoginFrom.login.getPlayerOneName;
import static com.chess.engine.board.Move.MoveFactory.createMove;
import static com.chess.gui.DebugPanel.getEloFromDatabase;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.*;

public final class Table {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Table INSTANCE = new Table();
    private final JFrame gameFrame;
    private final TakenPiecesPanel takenPiecesPanel;
    private final DebugPanel debugPanel;
    private final BoardPanel boardPanel;
    private final GameSetup gameSetup;
    ImageIcon icon = new ImageIcon("C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\src\\Images\\knightLogo.png");
    private GameHistoryPanel gameHistoryPanel;
    private MoveLog moveLog;
    private Board chessBoard;
    private Piece sourceTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private String pieceIconPath;
    private boolean highlightLegalMoves;
    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    public Table() {
        this.gameFrame = new JFrame("WP_Chess");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true;
        this.pieceIconPath = "art/pieces/simple/";
        this.gameHistoryPanel = new GameHistoryPanel();
        this.debugPanel = new DebugPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.gameFrame.setIconImage(icon.getImage());
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        center(this.gameFrame);
        setDefaultLookAndFeelDecorated(true);
    }

    public static Table get() {
        return INSTANCE;
    }

    private static void actionPerformed(ActionEvent e) {
        System.out.println("implement me");
        Table.get().highlightLegalMoves = false;
        Table.get().getGameFrame().repaint();
    }

    private static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }

    private static String playerInfo(final Player player) {
        return ("Player is: " + player.getAlliance() + "\nlegal moves (" + player.getLegalMoves().size() + ") = " + player.getLegalMoves() + "\ninCheck = " +
                player.isInCheck() + "\nisInCheckMate = " + player.isInCheckMate() +
                "\nisCastled = " + player.isCastled()) + "\n";
    }

    public JFrame getGameFrame() {
        return this.gameFrame;
    }


    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private boolean getHighlightLegalMoves() {
        return this.highlightLegalMoves;
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
    }

    private JMenu createFileMenu() {
        final JMenu filesMenu = new JMenu("File");
        filesMenu.setMnemonic(KeyEvent.VK_F);
        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(e -> {
            Table.get().getGameFrame().dispose();
            System.exit(0);
        });
        filesMenu.add(exitMenuItem);

        return filesMenu;
    }

    private JMenu createOptionsMenu() {

        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);

        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(e -> {
            Table.get().getGameFrame().dispose();
            Table.get().startNewGame();
        });
        optionsMenu.add(resetMenuItem);
        final JMenuItem escapeAnalysis = new JMenuItem("Escape Analysis Score", KeyEvent.VK_S);
        escapeAnalysis.addActionListener(e -> {
            final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
            if (lastMove != null) {
                System.out.println(MoveUtils.exchangeScore(lastMove));
            }
        });
        optionsMenu.add(escapeAnalysis);

        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
        legalMovesMenuItem.addActionListener(e -> {
            System.out.println(chessBoard.getWhitePieces());
            System.out.println(chessBoard.getBlackPieces());
            System.out.println(playerInfo(chessBoard.currentPlayer()));
            System.out.println(playerInfo(chessBoard.currentPlayer().getOpponent()));
        });
        optionsMenu.add(legalMovesMenuItem);
        return optionsMenu;
    }

    private JMenu createPreferencesMenu() {

        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
        colorChooserSubMenu.add(chooseLightMenuItem);

        final JMenuItem chooseLegalHighlightMenuItem = new JMenuItem(
                "Choose Legal Move Highlight Color");
        colorChooserSubMenu.add(chooseLegalHighlightMenuItem);
        preferencesMenu.add(colorChooserSubMenu);

        chooseDarkMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Dark Tile Color",
                    Table.get().getGameFrame().getBackground());
            if (colorChoice != null) {
                Table.get().getBoardPanel().setTileDarkColor(chessBoard, colorChoice);
            }
        });

        chooseLightMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Light Tile Color",
                    Table.get().getGameFrame().getBackground());
            if (colorChoice != null) {
                Table.get().getBoardPanel().setTileLightColor(chessBoard, colorChoice);
            }
        });

        final JMenu chessMenChoiceSubMenu = new JMenu("Choose Chess Men Image Set");

        final JMenuItem holyWarriorsMenuItem = new JMenuItem("Holy Warriors");
        chessMenChoiceSubMenu.add(holyWarriorsMenuItem);

        final JMenuItem rockMenMenuItem = new JMenuItem("Rock Men");
        chessMenChoiceSubMenu.add(rockMenMenuItem);

        final JMenuItem abstractMenMenuItem = new JMenuItem("Abstract Men");
        chessMenChoiceSubMenu.add(abstractMenMenuItem);

        final JMenuItem woodMenMenuItem = new JMenuItem("Wood Men");
        chessMenChoiceSubMenu.add(woodMenMenuItem);

        final JMenuItem fancyMenMenuItem = new JMenuItem("Fancy Men");
        chessMenChoiceSubMenu.add(fancyMenMenuItem);

        final JMenuItem fancyMenMenuItem2 = new JMenuItem("Fancy Men 2");
        chessMenChoiceSubMenu.add(fancyMenMenuItem2);

        woodMenMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            Table.get().getGameFrame().repaint();
        });

        holyWarriorsMenuItem.addActionListener(e -> {
//            pieceIconPath = "C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\art\\holywarriors";
            pieceIconPath="art/holywarriors/";
            Table.get().getBoardPanel().drawBoard(chessBoard);
        });

        rockMenMenuItem.addActionListener(e -> {
//            pieceIconPath = "C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\art\\";
            pieceIconPath="art/SciFicChessPieces/";

            Table.get().getBoardPanel().drawBoard(chessBoard);
        });

        abstractMenMenuItem.addActionListener(e -> {
//            pieceIconPath = "C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\art\\";
            pieceIconPath="art/pieces/";

            Table.get().getBoardPanel().drawBoard(chessBoard);
        });

        fancyMenMenuItem2.addActionListener(e -> {
//            pieceIconPath = "C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\art\\";
            pieceIconPath="art/fancy2/";

            Table.get().getBoardPanel().drawBoard(chessBoard);
        });

        fancyMenMenuItem.addActionListener(e -> {
//            pieceIconPath = "C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\art\\";
            pieceIconPath="art/fancy/";
            Table.get().getBoardPanel().drawBoard(chessBoard);
        });
        preferencesMenu.add(chessMenChoiceSubMenu);

        chooseLegalHighlightMenuItem.addActionListener(Table::actionPerformed);

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem("Highlight Legal Moves", true);
        cbLegalMoveHighlighter.addActionListener(e -> {
            highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
            boardPanel.drawBoard(chessBoard);
        });
        preferencesMenu.add(cbLegalMoveHighlighter);
        return preferencesMenu;


    }
    public void startNewGame() {
//        // Dispose of the old game frame
        Table.get().gameFrame.dispose();
//        // Reset the game state, clear move history, etc.
        this.chessBoard = Board.createStandardBoard();
        this.moveLog = new MoveLog();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.gameHistoryPanel.clear();
        this.takenPiecesPanel.clear();
//
        // Set up the new game
        this.boardPanel.drawBoard(chessBoard);
        this.highlightLegalMoves = true;
//
//        // Add the following lines to update the game history panel
        gameHistoryPanel.redo(chessBoard, moveLog);
        gameHistoryPanel.repaint(); // Ensure that the panel is repainted
//
        takenPiecesPanel.redo(moveLog);
        boardPanel.drawBoard(chessBoard);
        debugPanel.redo();

        // Show the new game frame
        this.gameFrame.setVisible(true);
//        Table.get().gameFrame.dispose();
//        Table t = new Table();
//        t.getGameFrame().dispose();
//        t.getGameFrame().setVisible(true);
    }

    private void updateUserData() {
        String currentUserName = getPlayerOneName();

        String currentElo = String.valueOf(getEloFromDatabase(currentUserName));
        int matchesPlayed = getMatchesPlayedFromDatabase(currentUserName);
        int matchesWon = getMatchesWonFromDatabase(currentUserName);
        int matchesLost = getMatchesLostFromDatabase(currentUserName);
        int matchesDrawn = getMatchesDrawnFromDatabase(currentUserName);
        updateUserDataInDatabase(currentUserName, currentElo, matchesPlayed, matchesWon, matchesLost, matchesDrawn);
    }

    private int getMatchesPlayedFromDatabase(String userName) {
        return getGameDataFromDatabase(userName, "played");
    }

    private int getMatchesWonFromDatabase(String userName) {
        return getGameDataFromDatabase(userName, "won");
    }

    private int getMatchesLostFromDatabase(String userName) {
        return getGameDataFromDatabase(userName, "lost");
    }

    private int getMatchesDrawnFromDatabase(String userName) {
        return getGameDataFromDatabase(userName, "draw");
    }

    private int getGameDataFromDatabase(String userName, String columnName) {
        int result = 0;
        String query = "SELECT " + columnName + " FROM player WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/chess", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt(columnName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving " + columnName + " data: " + e.getMessage());
        }
        return result;
    }
//🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 IInserting data of winning 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿
    private void updateUserDataInDatabase(String userName, String elo, int matchesPlayed, int matchesWon, int matchesLost, int matchesDrawn) {
        Connection connection;
        int _elo = Integer.parseInt(elo) + 9;
        int _matchesPlayed = matchesPlayed + 1;
        int _matchesWon = matchesWon + 1;
        try {
            String query = "UPDATE `player` SET `elo`='" + _elo + "',`played`='" + _matchesPlayed + "',`won`='" + _matchesWon + "' WHERE `userName`='" + userName + "'";
            connection = DriverManager.getConnection("jdbc:mysql://localhost/chess", "root", "");
            Statement s = connection.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Unable to update data in Performance Table in Table Class: " + e.getMessage());
        }
    }

    enum PlayerType {
        HUMAN,
        COMPUTER
    }

    enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        abstract BoardDirection opposite();

    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }


    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
            validate();
        }

        void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
            }
            validate();
            repaint();
        }

        void setTileDarkColor(final Board board,
                              final Color darkColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }
            drawBoard(board);
        }

        void setTileLightColor(final Board board,
                               final Color lightColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }
            drawBoard(board);
        }

    }

    private class TilePanel extends JPanel {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            highlightTileBorder(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
//                    if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) ||
//                            BoardUtils.isEndGame(Table.get().getGameBoard())) {
//                        return;
//                    }
                    if (isRightMouseButton(event)) {
                        sourceTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(event)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getPiece(tileId);
                            humanMovedPiece = sourceTile;
                        } else {
                            final Move move = createMove(chessBoard, sourceTile.getPiecePosition(), tileId);
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getToBoard();
                                moveLog.addMove(move);
                                if (chessBoard.currentPlayer().isInCheckMate()) {
                                    // Show dialog for checkmate
                                    int option = JOptionPane.showConfirmDialog(Table.get().getGameFrame(),
                                            "Checkmate! Do you want to play a new game?",
                                            "Game Over", JOptionPane.YES_NO_OPTION);

                                    if (option == JOptionPane.YES_OPTION) {
                                        Table.get().getGameFrame().dispose();
                                        Table.get().startNewGame();
//                                        Table t = new Table();
//                                        t.getGameFrame().dispose();
//                                        t.getGameFrame().setVisible(true);
                                        Table.get().updateUserData();

                                    } else {
                                        System.exit(0);
                                    }
                                }
                            }
                            sourceTile = null;
                            humanMovedPiece = null;
                        }
                    }
                    invokeLater(() -> {
                        highlightLegals(chessBoard);
                        gameHistoryPanel.redo(chessBoard, moveLog);
                        takenPiecesPanel.redo(moveLog);
                        boardPanel.drawBoard(chessBoard);
                        debugPanel.redo();
                    });
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }
            });
            validate();
        }

        void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTileBorder(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        private void highlightTileBorder(final Board board) {
            if (humanMovedPiece != null &&
                    humanMovedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance() &&
                    humanMovedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan, 2));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
        }

        private void highlightLegals(final Board board) {
            if (Table.get().getHighlightLegalMoves()) {
                Collection<Move> legalMoves = pieceLegalMoves(board);
                for (final Move move : legalMoves) {
                    if (move.getDestinationCoordinate() == this.tileId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                        } catch (final IOException e) {
                            System.out.println("Highlight Legal Moves Is Not Working!");
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getPiece(this.tileId) != null) {
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getPiece(this.tileId).getPieceAllegiance().toString().charAt(0) +
                            board.getPiece(this.tileId).toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (final IOException e) {
                    System.out.println("Chess Pieces Are Not Loading!");
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.THIRD_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.INSTANCE.SECOND_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }


}
