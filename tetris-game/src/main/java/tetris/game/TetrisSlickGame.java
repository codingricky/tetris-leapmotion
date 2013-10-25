package tetris.game;

import core.tetris.Direction;
import core.tetris.TetrisGame;

public class TetrisSlickGame {

    private final TetrisGame tetrisGame;
    private final GameConfig gameConfig;

    public TetrisSlickGame(GameConfig gameConfig) {
        this.tetrisGame = new TetrisGame();
        this.gameConfig = gameConfig;
    }

    public void tick() {
        tetrisGame.tick();
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void startGame() {
        tetrisGame.startGame();
    }

    public void move(Direction direction) {
        tetrisGame.movePiece(direction);
    }

    public TetrisGame getTetrisGame() {
        return tetrisGame;
    }
}
