package tetris.game;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.sun.istack.internal.logging.Logger;
import core.tetris.Direction;
import core.tetris.TetrisGame;

public class LeapTetrisListener extends Listener {

    private static final Logger LOGGER = Logger.getLogger(LeapTetrisListener.class);

    private final TetrisGame tetrisGame;
    private float lastX;
    private float lastY;

    private long lastRotation;

    public LeapTetrisListener(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    public void onInit(Controller controller) {
        LOGGER.info("onInit");
    }

    public void onConnect(Controller controller) {
        LOGGER.info("onConnect");
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    }

    public void onFrame(Controller controller) {
        for (Gesture gesture : controller.frame().gestures()) {
            final boolean isCircle = gesture.type() == Gesture.Type.TYPE_CIRCLE;
            final boolean isStopped = gesture.state() == Gesture.State.STATE_STOP;
            final boolean isSufficientTimeBetweenRotations = (System.currentTimeMillis() - lastRotation) > 1000;
            if (gesture.isValid() && isCircle && isStopped && isSufficientTimeBetweenRotations) {
                tetrisGame.movePiece(Direction.ROTATE);
                lastRotation = System.currentTimeMillis();
                return;
            }
        }

        final Hand hand = controller.frame().hands().get(0);
        movePiece(hand.palmPosition().getX(), hand.palmPosition().getY());
    }

    private void movePiece(float x, float y) {
        final int horizontalMovement = (int) ((x - lastX) / 5);
        final int verticalMovement = (int) ((y - lastY) / 5);

        if (horizontalMovement != 0) {
            Direction direction = x > lastX ? Direction.RIGHT : Direction.LEFT;
            LOGGER.info("Moving piece " + direction + " " + horizontalMovement + " times.");
            tetrisGame.movePieceNTimes(direction, Math.abs(horizontalMovement));
            lastX = x;
        }

        if (verticalMovement != 0) {
            LOGGER.info("Moving piece " + Direction.DOWN + " " + verticalMovement + " times.");
            final boolean isDirectionDown = y < lastY;
            if (isDirectionDown) {
                tetrisGame.movePieceNTimes(Direction.DOWN, Math.abs(verticalMovement));
            }
            lastY = y;
        }
    }

}