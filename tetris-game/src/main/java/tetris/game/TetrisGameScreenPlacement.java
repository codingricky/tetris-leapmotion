package tetris.game;

public class TetrisGameScreenPlacement {

    private static final int FIXED_X_OFFSET = 30;
    public static final int FIXED_Y_OFFSET = 30;
    private final int scoreX;
    private final int scoreY;

    private final int boardX;
    private final int boardY;

    public TetrisGameScreenPlacement(int currentColumnInRow, int currentRow) {
        scoreX = FIXED_X_OFFSET + currentColumnInRow * 500;
        scoreY = FIXED_Y_OFFSET + currentRow * 700;

        boardX = FIXED_X_OFFSET + currentColumnInRow * 500;
        boardY = FIXED_Y_OFFSET + currentRow * 700;
    }

    public int getScoreXOffset() {
        return scoreX;
    }

    public int getScoreYOffset() {
        return scoreY;
    }

    public int getBoardXOffset() {
        return boardX;
    }

    public int getBoardYOffset() {
        return boardY;
    }
}
