package tetris.game;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import core.tetris.Direction;
import core.tetris.PieceType;
import core.tetris.TetrisGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import java.util.HashMap;
import java.util.Map;

/**
 * A game using Slick2d
 */
public class Game extends BasicGame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 768;

    private static final int SCORE_X = WIDTH - 100;
    private static final int SCORE_Y = 15;

    private static final int BOARD_X_OFFSET = 30;
    private static final int BOARD_Y_OFFSET = 30;

    private Listener listener;
    private Controller leapController;

    private Command left = new TetrisCommand(Direction.LEFT);
    private Command right = new TetrisCommand(Direction.RIGHT);
    private Command down = new TetrisCommand(Direction.DOWN);
    private Command rotate = new TetrisCommand(Direction.ROTATE);
    private Command fall = new TetrisCommand(Direction.FALL);

    private TetrisGame tetrisGame;
    private Map<PieceType, Image> pieceTypeToImageMap = new HashMap<>();
    private LeapListener leapListener;

    public Game() {
        super("Tetris LeapMotion");
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        Image sampleImage = pieceTypeToImageMap.get(PieceType.I_PIECE);
        g.drawRect(BOARD_X_OFFSET, BOARD_Y_OFFSET, tetrisGame.getX() * sampleImage.getWidth(), tetrisGame.getY() * sampleImage.getHeight());
        drawBoard(g);

        String score = String.format("Score:%d", tetrisGame.getScore());

        g.drawString(score, SCORE_X, SCORE_Y);
    }

    private void drawBoard(Graphics g) {
        for (int x = 0; x < tetrisGame.getX(); x++) {
            for (int y = 0; y < tetrisGame.getY(); y++) {
                PieceType pieceAt = tetrisGame.getPieceAt(x, y);
                if (pieceAt != null) {
                    Image image = pieceTypeToImageMap.get(pieceAt);
                    int xPosition = x * image.getWidth();
                    int yPosition = y * image.getHeight();
                    g.drawImage(image, xPosition + BOARD_X_OFFSET, yPosition + BOARD_Y_OFFSET);
                }
            }
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        tetrisGame = new TetrisGame();
        tetrisGame.startGame();

        InputProvider provider = new InputProvider(container.getInput());
        provider.addListener(new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                TetrisCommand tetrisCommand = (TetrisCommand) command;
                tetrisGame.movePiece(tetrisCommand.getDirection());
            }

            @Override
            public void controlReleased(Command command) {
            }
        });
        provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
        provider.bindCommand(new KeyControl(Input.KEY_DOWN), down);
        provider.bindCommand(new KeyControl(Input.KEY_UP), rotate);
        provider.bindCommand(new KeyControl(Input.KEY_SPACE), fall);


        pieceTypeToImageMap.put(PieceType.I_PIECE, new Image("images/aqua.png"));
        pieceTypeToImageMap.put(PieceType.J_PIECE, new Image("images/blue.png"));
        pieceTypeToImageMap.put(PieceType.O_PIECE, new Image("images/green.png"));
        pieceTypeToImageMap.put(PieceType.L_PIECE, new Image("images/orange.png"));
        pieceTypeToImageMap.put(PieceType.S_PIECE, new Image("images/purple.png"));
        pieceTypeToImageMap.put(PieceType.T_PIECE, new Image("images/red.png"));
        pieceTypeToImageMap.put(PieceType.Z_PIECE, new Image("images/yellow.png"));

        initLeapMotion();
    }

    private void initLeapMotion() {
        leapController = new Controller();
        leapListener = new LeapListener(tetrisGame);
        leapController.addListener(leapListener);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        tetrisGame.tick();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setForceExit(false);
        app.start();
    }

}
