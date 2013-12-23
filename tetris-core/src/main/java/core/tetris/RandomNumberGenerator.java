package core.tetris;

import java.util.Random;

public class RandomNumberGenerator {

    private final Random random = new Random();

    public int getRandom() {
        return random.nextInt(PieceType.getMaxNumberOfPieces());
    }
}