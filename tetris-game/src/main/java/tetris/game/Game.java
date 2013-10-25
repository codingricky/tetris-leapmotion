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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends BasicGame {

    private static final int NUMBER_OF_GAMES = 2;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 768;

    private static final int GAME_1_SCORE_X_OFFSET = 10;
    private static final int GAME_1_SCORE_Y_OFFSET = 15;
    private static final int GAME_1_BOARD_X_OFFSET = 30;
    private static final int GAME_1_BOARD_Y_OFFSET = 30;

    private Command left = new TetrisCommand(Direction.LEFT);
    private Command right = new TetrisCommand(Direction.RIGHT);
    private Command down = new TetrisCommand(Direction.DOWN);
    private Command rotate = new TetrisCommand(Direction.ROTATE);
    private Command fall = new TetrisCommand(Direction.FALL);

    private List<TetrisSlickGame> games = new ArrayList<>();
    private Map<PieceType, Image> pieceTypeToImageMap = new HashMap<>();

    public Game() {
        super("Tetris LeapMotion");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        for (TetrisSlickGame game : games) {
            drawBoard(g, game.getTetrisGame(), game.getGameConfig());
        }
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
        int scoreXOffset = GAME_1_SCORE_X_OFFSET;
        int scoreYOffset = GAME_1_SCORE_Y_OFFSET;
        int boardXOffset = GAME_1_BOARD_X_OFFSET;
        int boardYOffset = GAME_1_BOARD_Y_OFFSET;

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            TetrisSlickGame game = new TetrisSlickGame(new GameConfig(scoreXOffset, scoreYOffset, boardXOffset, boardYOffset));
            game.startGame();
            games.add(game);

            scoreXOffset += 500;
            boardXOffset += 500;
        }

        InputProvider provider = new InputProvider(container.getInput());
        provider.addListener(new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                TetrisCommand tetrisCommand = (TetrisCommand) command;
                for (TetrisSlickGame game : games) {
                    game.move(tetrisCommand.getDirection());
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
        for (TetrisSlickGame game : games) {
            game.tick();
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH * NUMBER_OF_GAMES, HEIGHT, false);
        app.setForceExit(false);
        app.start();
    }

}
