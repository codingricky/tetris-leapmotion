package tetris.game;

import java.util.HashMap;
import java.util.Map;

import core.tetris.Direction;
import core.tetris.PieceType;
import core.tetris.TetrisGame;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

public class Game extends BasicGame {

    private static final int NUMBER_OF_GAMES = 1;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 1000;

    private static final int NUMBER_OF_GAMES_PER_ROW = 5;

    static {
        assert(NUMBER_OF_GAMES_PER_ROW >= 1);
        assert(NUMBER_OF_GAMES >= 1);
    }

    // commands
    private final Command left = new TetrisCommand(Direction.LEFT);
    private final Command right = new TetrisCommand(Direction.RIGHT);
    private final Command down = new TetrisCommand(Direction.DOWN);
    private final Command rotate = new TetrisCommand(Direction.ROTATE);
    private final Command fall = new TetrisCommand(Direction.FALL);
    private final Command addGame = new BasicCommand("Add Game");
    private final Command previousGame = new BasicCommand("Previous Game");
    private final Command nextGame = new BasicCommand("Next Game");

    private final AllGames games = new AllGames();
    private final Map<PieceType, Image> pieceTypeToImageMap = new HashMap<>();

    public Game() {
        super("Tetris LeapMotion");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        int numberOfGames = games.numberOfGames();
        float scale = (float) Math.log(numberOfGames);
        scale = 1/scale;
        scale = Math.min(scale, 1);
        g.scale(scale, scale);


        int currentColumnInRow = 0;
        int currentRow = 0;
        for (int i = 0; i < games.getGames().size(); i++) {
            TetrisGame currentGame = games.getGames().get(i);
            drawBoard(g, currentGame, new TetrisGameScreenPlacement(currentColumnInRow, currentRow));
            currentColumnInRow++;
            if (currentColumnInRow == NUMBER_OF_GAMES_PER_ROW) {
                currentColumnInRow = 0;
                currentRow++;
            }
        }
    }

    private void drawBoard(Graphics g, TetrisGame tetrisGame, TetrisGameScreenPlacement screenPlacement) {
        Image sampleImage = pieceTypeToImageMap.get(PieceType.I_PIECE);
        boolean isGameCurrent = games.isGameCurrent(tetrisGame);
        Color color = isGameCurrent ? Color.red : Color.white;
        g.setColor(color);

        g.drawRect(screenPlacement.getBoardXOffset(),
                screenPlacement.getBoardYOffset(),
                tetrisGame.getX() * sampleImage.getWidth(),
                tetrisGame.getY() * sampleImage.getHeight());

        for (int x = 0; x < tetrisGame.getX(); x++) {
            for (int y = 0; y < tetrisGame.getY(); y++) {
                PieceType pieceAt = tetrisGame.getPieceAt(x, y);
                if (pieceAt != null) {
                    Image image = pieceTypeToImageMap.get(pieceAt);
                    int xPosition = x * image.getWidth();
                    int yPosition = y * image.getHeight();
                    g.drawImage(image, xPosition + screenPlacement.getBoardXOffset(), yPosition + screenPlacement.getBoardYOffset());
                }
            }
        }

        String score = String.format("Score:%d", tetrisGame.getScore());
        g.drawString(score, screenPlacement.getScoreXOffset(), screenPlacement.getScoreYOffset());
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        buildPieceTypeToImageMap();

        games.createGames(NUMBER_OF_GAMES);
        games.allStart();

        InputProvider provider = new InputProvider(container.getInput());
        provider.addListener(new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                if (command instanceof TetrisCommand) {
                    TetrisCommand tetrisCommand = (TetrisCommand) command;
                    games.moveCurrentGame(tetrisCommand.getDirection());
                } else {
                    if (command == nextGame) {
                        games.moveToNextGame();
                    } else if (command == previousGame) {
                        games.moveToPreviousGame();
                    } else if (command == addGame) {
                        games.addGame();
                    }
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
        provider.bindCommand(new KeyControl(Input.KEY_2), nextGame);
        provider.bindCommand(new KeyControl(Input.KEY_1), previousGame);
        provider.bindCommand(new KeyControl(Input.KEY_3), addGame);
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

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        games.allTick();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH * 5, HEIGHT, false);
        app.setForceExit(false);
        app.start();
    }

}
