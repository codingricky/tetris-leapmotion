package core.tetris;

import static core.tetris.Direction.DOWN;
import static core.tetris.Direction.FALL;
import static core.tetris.Direction.LEFT;
import static core.tetris.Direction.RIGHT;
import static core.tetris.Direction.ROTATE;

public class TetrisPiece {

    private final PieceType type;
    private final TetrisBoard board;

    private int rotation;
    private int maxRotate;
    private Point centrePoint = new Point();
    private Point[] blocks = new Point[4];

    public TetrisPiece(PieceType type, TetrisBoard board) {
        this.type = type;
        this.board = board;
        initializeBlocks();
    }

    public TetrisPiece(TetrisPiece piece) {
        this.type = piece.type;
        this.board = piece.board;
        initializeBlocks();
    }

    public boolean move(Direction direction) {
        boolean result = true;

        if (direction == FALL) {
            boolean loop = true;

            while (loop) {
                board.removePiece(this);
                centrePoint = centrePoint.drop();

                if (board.willFit(this)) {
                    board.addPiece(this);
                } else {
                    centrePoint = centrePoint.unDrop();
                    board.addPiece(this);
                    loop = false;
                    result = false;
                }
            }
        } else {
            board.removePiece(this);

            doMove(direction);

            if (board.willFit(this)) {
                board.addPiece(this);
            } else {
                undoMove(direction);
                board.addPiece(this);
                result = false;
            }
        }

        return result;
    }

    private void doMove(Direction direction) {
        if (direction == LEFT) {
            centrePoint = centrePoint.left();
        } else if (direction == RIGHT) {
            centrePoint = centrePoint.right();
        } else if (direction == DOWN) {
            centrePoint = centrePoint.drop();
        } else if (direction == ROTATE) {
            rotateClockwise();
        }
    }

    private void undoMove(Direction direction) {
        if (direction == LEFT) {
            centrePoint = centrePoint.right();
        } else if (direction == RIGHT) {
            centrePoint = centrePoint.left();
        } else if (direction == DOWN) {
            centrePoint = centrePoint.unDrop();
        } else if (direction == ROTATE) {
            rotateAntiClockwise();
        }
    }

    public Point getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(Point point) {
        centrePoint = point;
    }

    public Point[] getRelativePoints() {
        return blocks;
    }

    public PieceType getType() {
        return type;
    }

    private void initializeBlocks() {
        switch (type) {
            case I_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(-1, 0);
                blocks[2] = new Point(1, 0);
                blocks[3] = new Point(2, 0);
                maxRotate = 2;
                break;

            case L_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(-1, 0);
                blocks[2] = new Point(-1, 1);
                blocks[3] = new Point(1, 0);
                maxRotate = 4;
                break;

            case J_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(-1, 0);
                blocks[2] = new Point(1, 0);
                blocks[3] = new Point(1, 1);
                maxRotate = 4;
                break;

            case Z_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(-1, 0);
                blocks[2] = new Point(0, 1);
                blocks[3] = new Point(1, 1);
                maxRotate = 2;
                break;

            case S_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(1, 0);
                blocks[2] = new Point(0, 1);
                blocks[3] = new Point(-1, 1);
                maxRotate = 2;
                break;

            case O_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(0, 1);
                blocks[2] = new Point(-1, 0);
                blocks[3] = new Point(-1, 1);
                maxRotate = 1;
                break;

            case T_PIECE:
                blocks[0] = new Point(0, 0);
                blocks[1] = new Point(-1, 0);
                blocks[2] = new Point(1, 0);
                blocks[3] = new Point(0, 1);
                maxRotate = 4;
                break;
        }
    }

    private void rotateClockwise() {
        if (maxRotate > 1) {
            rotation++;

            if (maxRotate == 2 && rotation == 2) {
                // Rotate Anti-Clockwise
                rotateClockwiseNow();
                rotateClockwiseNow();
                rotateClockwiseNow();
            } else {
                rotateClockwiseNow();
            }
        }

        rotation = rotation % maxRotate;
    }

    private void rotateAntiClockwise() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private void rotateClockwiseNow() {
        for (int count = 0; count < 4; count++) {
            blocks[count] = blocks[count].rotateClockWise();
        }
    }

}
