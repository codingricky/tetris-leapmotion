package tetris.game;

import com.leapmotion.leap.Controller;
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

public class Game extends BasicGame {

    private static final int WIDTH = 380;
    private static final int HEIGHT = 700;

    private static final int FIXED_X_OFFSET = 30;
    private static final int FIXED_Y_OFFSET = 40;

    private static final Command left = new TetrisCommand(Direction.LEFT);
    private static final Command right = new TetrisCommand(Direction.RIGHT);
    private static final Command down = new TetrisCommand(Direction.DOWN);
    private static final Command rotate = new TetrisCommand(Direction.ROTATE);
    private static final Command fall = new TetrisCommand(Direction.FALL);

    private final TetrisGame tetrisGame;
    private final Map<PieceType, Image> pieceTypeToImageMap;

    public Game() {
        super("Tetris LeapMotion");
        tetrisGame = new TetrisGame();
        pieceTypeToImageMap = new HashMap<>();

        Controller leapController = new Controller();
        LeapTetrisListener leapTetrisListener = new LeapTetrisListener(tetrisGame);
        leapController.addListener(leapTetrisListener);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        buildPieceTypeToImageMap();
        tetrisGame.startGame();

        InputProvider provider = new InputProvider(container.getInput());
        provider.addListener(new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                if (command instanceof TetrisCommand) {
                    TetrisCommand tetrisCommand = (TetrisCommand) command;
                    tetrisGame.movePiece(tetrisCommand.getDirection());
                }
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
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        drawBoard(g, tetrisGame);
    }

    private void drawBoard(Graphics graphics, TetrisGame tetrisGame) {
        Image sampleImage = pieceTypeToImageMap.get(PieceType.I_PIECE);
        graphics.drawRect(FIXED_X_OFFSET,
                FIXED_X_OFFSET,
                tetrisGame.getX() * sampleImage.getWidth(),
                tetrisGame.getY() * sampleImage.getHeight());

        for (int x = 0; x < tetrisGame.getX(); x++) {
            for (int y = 0; y < tetrisGame.getY(); y++) {
                PieceType pieceAt = tetrisGame.getPieceAt(x, y);
                if (pieceAt != null) {
                    Image image = pieceTypeToImageMap.get(pieceAt);
                    int xPosition = x * image.getWidth();
                    int yPosition = y * image.getHeight();
                    graphics.drawImage(image,
                            xPosition + FIXED_X_OFFSET,
                            yPosition + FIXED_Y_OFFSET);
                }
            }
        }

        String score = String.format("Score:%d", tetrisGame.getScore());
        graphics.drawString(score, FIXED_X_OFFSET, FIXED_Y_OFFSET);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        tetrisGame.tick();
    }

    private void buildPieceTypeToImageMap() throws SlickException {
        pieceTypeToImageMap.put(PieceType.I_PIECE, new Image("images/aqua.png"));
        pieceTypeToImageMap.put(PieceType.J_PIECE, new Image("images/blue.png"));
        pieceTypeToImageMap.put(PieceType.O_PIECE, new Image("images/green.png"));
        pieceTypeToImageMap.put(PieceType.L_PIECE, new Image("images/orange.png"));
        pieceTypeToImageMap.put(PieceType.S_PIECE, new Image("images/purple.png"));
        pieceTypeToImageMap.put(PieceType.T_PIECE, new Image("images/red.png"));
        pieceTypeToImageMap.put(PieceType.Z_PIECE, new Image("images/yellow.png"));
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setForceExit(false);
        app.start();
    }
}
