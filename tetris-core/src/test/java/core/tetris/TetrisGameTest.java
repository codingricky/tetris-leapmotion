package core.tetris;

import org.junit.Before;
import org.junit.Test;

public class TetrisGameTest {

    private TetrisGame tetrisGame;

    @Before
    public void before() {
        tetrisGame = new TetrisGame();
    }

    @Test
    public void playAGame() {

        tetrisGame.startGame();
    }
}
