package tetris.game;

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

    private static final int WIDTH = 500 * 2;
    private static final int HEIGHT = 768;

    // offsets for game 1
    private static final int GAME_1_SCORE_X_OFFSET = 10;
    private static final int GAME_1_SCORE_Y_OFFSET = 15;
    private static final int GAME_1_BOARD_X_OFFSET = 30;
    private static final int GAME_1_BOARD_Y_OFFSET = 30;

    // offsets for game 2
    private static final int GAME_2_SCORE_X_OFFSET = GAME_1_SCORE_X_OFFSET + 500;
    private static final int GAME_2_SCORE_Y_OFFSET = GAME_1_SCORE_Y_OFFSET;
    private static final int GAME_2_BOARD_X_OFFSET = GAME_1_SCORE_X_OFFSET + 500;
    private static final int GAME_2_BOARD_Y_OFFSET = GAME_1_BOARD_Y_OFFSET;

    private Command left = new TetrisCommand(Direction.LEFT);
    private Command right = new TetrisCommand(Direction.RIGHT);
    private Command down = new TetrisCommand(Direction.DOWN);
    private Command rotate = new TetrisCommand(Direction.ROTATE);
    private Command fall = new TetrisCommand(Direction.FALL);

    private TetrisGame tetrisGame1;
    private GameConfig gameConfigForTetrisGame1;

    private TetrisGame tetrisGame2;
    private GameConfig gameConfigForTetrisGame2;

    private Map<PieceType, Image> pieceTypeToImageMap = new HashMap<>();

    public Game() {
        super("Tetris LeapMotion");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        drawBoard(g, tetrisGame1, gameConfigForTetrisGame1);
        drawBoard(g, tetrisGame2, gameConfigForTetrisGame2);
    }

    private void drawBoard(Graphics g, TetrisGame tetrisGame, GameConfig gameConfig) {
        Image sampleImage = pieceTypeToImageMap.get(PieceType.I_PIECE);
        g.drawRect(gameConfig.getBoardXOffset(),
                   gameConfig.getBoardYOffset(),
                tetrisGame.getX() * sampleImage.getWidth(),
                tetrisGame.getY() * sampleImage.getHeight());

        for (int x = 0; x < tetrisGame.getX(); x++) {
            for (int y = 0; y < tetrisGame.getY(); y++) {
                PieceType pieceAt = tetrisGame.getPieceAt(x, y);
                if (pieceAt != null) {
                    Image image = pieceTypeToImageMap.get(pieceAt);
                    int xPosition = x * image.getWidth();
                    int yPosition = y * image.getHeight();
                    g.drawImage(image, xPosition + gameConfig.getBoardXOffset(), yPosition + gameConfig.getBoardYOffset());
                }
            }
        }

        String score = String.format("Score:%d", tetrisGame.getScore());
        g.drawString(score, gameConfig.getScoreXOffset(), gameConfig.getScoreYOffset());
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        tetrisGame1 = new TetrisGame();
        tetrisGame1.startGame();
        gameConfigForTetrisGame1 = new GameConfig(GAME_1_SCORE_X_OFFSET,
                GAME_1_SCORE_Y_OFFSET,
                GAME_1_BOARD_X_OFFSET,
                GAME_1_BOARD_Y_OFFSET);

        tetrisGame2 = new TetrisGame();
        tetrisGame2.startGame();
        gameConfigForTetrisGame2 = new GameConfig(GAME_2_SCORE_X_OFFSET,
                GAME_2_SCORE_Y_OFFSET,
                GAME_2_BOARD_X_OFFSET,
                GAME_2_BOARD_Y_OFFSET);

        InputProvider provider = new InputProvider(container.getInput());
        provider.addListener(new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                TetrisCommand tetrisCommand = (TetrisCommand) command;
                tetrisGame1.movePiece(tetrisCommand.getDirection());
                tetrisGame2.movePiece(tetrisCommand.getDirection());
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

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        tetrisGame1.tick();

        tetrisGame2.tick();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setForceExit(false);
        app.start();
    }

}
