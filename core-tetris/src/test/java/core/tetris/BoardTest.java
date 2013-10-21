package core.tetris;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class BoardTest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    private Board board;

    @Before
    public void before() {
        board = new Board(WIDTH, HEIGHT);
    }

    @Test
    public void testGetters() {
        assertThat(board.getX()).isEqualTo(WIDTH);
        assertThat(board.getY()).isEqualTo(HEIGHT);
        assertThat(board.getRows()).isEqualTo(WIDTH);
    }

    @Test
    public void testSetPiece() {
        board.setPieceAt(0, 0, PieceType.I_PIECE);
        assertThat(board.getPieceAt(0, 0)).isEqualTo(PieceType.I_PIECE);
    }

    @Test
    public void testInitialState() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                assertThat(board.getPieceAt(x, y)).isEqualTo()
            }
        }
    }
}
