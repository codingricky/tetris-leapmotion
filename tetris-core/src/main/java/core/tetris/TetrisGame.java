package core.tetris;

public class TetrisGame {

    private static final int NUMBER_OF_COLS = 10;
    private static final int NUMBER_OF_ROWS = 20;

    private final GameState gameState;
    private final TetrisBoard board;
    
    private TetrisPieceFactory tetrisPieceFactory;
    private TetrisPiece currentPiece;
    private long lastUpdate = 0;

    public TetrisGame() {
        board = new TetrisBoard(NUMBER_OF_COLS, NUMBER_OF_ROWS);
        gameState = new GameState();
        tetrisPieceFactory = new TetrisPieceFactory(board);
    }

    public void startGame() {
        if (!gameState.isPlaying()) {
            board.resetBoard();
            gameState.reset();
            gameState.startPlaying();
            currentPiece = null;
        }
    }

    public PieceType getPieceAt(int x, int y) {
        return board.getPieceAt(x, y);
    }

    public boolean hasPieceAt(int x, int y) {
        return getPieceAt(x, y) != null;
    }

    public int getX() {
        return board.getColumns();
    }

    public int getY() {
        return board.getRows();
    }

    public void stopGame() {
        gameState.stopPlaying();
    }

    public void movePieceNTimes(Direction direction, int times) {
        for (int i = 0; i < times; i++) {
            movePiece(direction);
        }
    }

    public void movePiece(Direction direction) {
        if (direction == null) {
            return;
        }

        if (currentPiece != null && gameState.isPlaying()) {
            if (direction == Direction.DOWN || direction == Direction.FALL) {
                // If it won't go any further then drop it there.
                if (!currentPiece.move(direction)) {
                    currentPiece = null;
                }
            } else {
                currentPiece.move(direction);
            }
        }
    }

    public void tick() {
        long timeSinceLastUpdate = System.currentTimeMillis() - lastUpdate;
        if (timeSinceLastUpdate < gameState.getDelay()) {
            return;
        }

        lastUpdate = System.currentTimeMillis();

        if (gameState.isPlaying()) {
            if (currentPiece == null) {
                int completedLines = board.getNumberOfRowsThatWillBeRemoved();
                board.removeCompletedLines();

                if (completedLines > 0) {
                    gameState.incrementTotalLinesBy(completedLines);
                    gameState.updateDelay();
                    gameState.updateScore(completedLines);
                }

                currentPiece = tetrisPieceFactory.getPiece(board);

                if (board.willFit(currentPiece)) {
                    board.addPiece(currentPiece);
                } else {
                    board.addPiece(currentPiece);
                    stopGame();
                }
            } else {
                movePiece(Direction.DOWN);
            }
        }
    }

    public int getScore() {
        return gameState.getScore();
    }

    public void setTetrisPieceFactory(TetrisPieceFactory tetrisPieceFactory) {
        this.tetrisPieceFactory = tetrisPieceFactory;
    }
}
