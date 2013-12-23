package tetris.game;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Listener;
import core.tetris.Direction;
import core.tetris.TetrisGame;

public class LeapTetrisListener extends Listener {

    private final TetrisGame tetrisGame;
    private float lastX;
    private float lastY;
    private long lastRotation;

    public LeapTetrisListener(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    }

    public void onFrame(Controller controller) {

        for (Gesture gesture : controller.frame().gestures()) {
            final boolean isCircle = gesture.type() == Gesture.Type.TYPE_CIRCLE;
            final boolean isStopped = gesture.state() == Gesture.State.STATE_STOP;
            final boolean isSufficientTimeBetweenRotations = (System.currentTimeMillis() - lastRotation) > 500;
            if (gesture.isValid() && isCircle && isStopped && isSufficientTimeBetweenRotations) {
                tetrisGame.movePiece(Direction.ROTATE);
                lastRotation = System.currentTimeMillis();
                return;
            }
        }

        float x = controller.frame().hands().get(0).palmPosition().get(0);
        movePiece(x, 0);
    }

    public void movePiece(float x, float y) {
        final int horizontalMovement = (int) ((x - lastX)/5);
        final int verticalMovement = (int) ((y - lastY)/5);

        System.out.println("h=" + horizontalMovement + ",v=" + verticalMovement);
        if (horizontalMovement != 0) {
            Direction direction = x > lastX ? Direction.RIGHT : Direction.LEFT;
            tetrisGame.movePieceNTimes(direction, horizontalMovement);
            lastX = x;

        } else {
//            if (verticalMovement > 0) {
//                tetrisGame.movePieceNTimes(Direction.DOWN, verticalMovement);
//            }
            lastY = y;
        }
    }
}
