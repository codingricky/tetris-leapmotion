package core.tetris;

public class Board {

    private final PieceType[][] matrix;
    private final int maxX;
    private final int maxY;

    public Board(int x, int y) {
        this.matrix = new PieceType[x][y];
        this.maxX = x;
        this.maxY = y;
    }

    public int getX() {
        return maxX;
    }

    public int getY() {
        return maxY;
    }

    public int getRows() {
        return maxY;
    }

    public PieceType getPieceAt(int x, int y) {
        return matrix[x][y];
    }

    public Board setPieceAt(int x, int y, PieceType value) {
        matrix[x][y] = value;
        return this;
    }
}
