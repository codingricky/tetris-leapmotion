package tetris.game;

public class TetrisGameScreenPlacement {

    private int scoreX;
    private int scoreY;

    private int boardX;
    private int boardY;

    public TetrisGameScreenPlacement(int scoreX, int scoreY, int boardX, int boardY) {
        this.scoreX = scoreX;
        this.scoreY = scoreY;
        this.boardX = boardX;
        this.boardY = boardY;
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

    public TetrisGameScreenPlacement getNextScreenPlacement(int numberOfGamesPerRow) {
        return new TetrisGameScreenPlacement(scoreX + 500, scoreY, boardX + 500, boardY);
    }
}
