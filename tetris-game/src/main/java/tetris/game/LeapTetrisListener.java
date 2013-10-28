package tetris.game;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import core.tetris.Direction;

public class LeapTetrisListener extends Listener {

    private final AllGames allGames;

    public LeapTetrisListener(AllGames allGames) {
        this.allGames = allGames;
    }

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        Hand leftHand = frame.hands().leftmost();
        Hand rightHand = frame.hands().rightmost();

        controller.frame().gestures().forEach(gesture -> {
            if (gesture.isValid() && gesture.state() == Gesture.State.STATE_STOP) {
                Hand hand = gesture.hands().get(0);
                if (hand.id() == leftHand.id()) {
                    if (gesture.type() == Gesture.Type.TYPE_SWIPE) {
                        SwipeGesture swipeGesture = new SwipeGesture(gesture);
                        if (swipeGesture.direction().getX() < 0) {
                            allGames.moveToPreviousGame();
                        } else if (swipeGesture.direction().getX() > 0) {
                            allGames.moveToNextGame();
                        }
                    } else if (gesture.type() == Gesture.Type.TYPE_SCREEN_TAP) {
                        allGames.addGame();
                    } else if (gesture.type() == Gesture.Type.TYPE_CIRCLE) {
                        allGames.deleteGame();
                    }
                } else if (hand.id() == rightHand.id()) {
                    if (gesture.type() == Gesture.Type.TYPE_SWIPE) {
                        SwipeGesture swipeGesture = new SwipeGesture(gesture);
                        if (swipeGesture.direction().getX() < 0) {
                            allGames.moveCurrentGame(Direction.LEFT);
                        } else if (swipeGesture.direction().getX() > 0) {
                            allGames.moveCurrentGame(Direction.RIGHT);
                        }
                    } else if (gesture.type() == Gesture.Type.TYPE_CIRCLE) {
                        allGames.moveCurrentGame(Direction.ROTATE);
                    }
                }
            }
        });
    }
}
