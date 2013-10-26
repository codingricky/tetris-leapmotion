package tetris.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import core.tetris.Direction;
import core.tetris.TetrisGame;

public class AllGames {

    private final List<TetrisGame> games = new ArrayList<>();
    private int currentGame;

    public AllGames() {
        this.currentGame = 0;
    }

    public void allTick() {
        games.forEach(TetrisGame::tick);
    }

    public void allMove(Direction direction) {
        games.forEach(game -> game.movePiece(direction));
    }

    public void createGames(int numberOfGames) {
        for (int i = 0; i < numberOfGames; i++) {
            games.add(new TetrisGame());
        }
    }

    public void forEach(Consumer<TetrisGame> consumer) {
        games.forEach(consumer);
    }

    public List<TetrisGame> getGames() {
        return Collections.unmodifiableList(games);
    }

    public void allStart() {
        games.forEach(TetrisGame::startGame);
    }

    public void tickCurrentGame() {
        games.get(currentGame).tick();
    }

    public boolean isGameCurrent(TetrisGame tetrisGame) {
        return games.get(currentGame) == tetrisGame;
    }

    public void moveCurrentGame(Direction direction) {
        games.get(currentGame).movePiece(direction);
    }

    public void moveToNextGame() {
        int potentialNextGame = currentGame + 1;
        if (games.size() == potentialNextGame) {
            potentialNextGame = 0;
        }
        currentGame = potentialNextGame;
    }

    public void moveToPreviousGame() {
        int potentialPreviousGame = currentGame - 1;
        if (potentialPreviousGame < 0) {
            potentialPreviousGame = games.size() - 1;
        }
        currentGame = potentialPreviousGame;
    }

    public void addGame() {
        TetrisGame game = new TetrisGame();
        games.add(game);
        game.startGame();
    }

    public int numberOfGames() {
        return games.size();
    }
}
