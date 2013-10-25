package tetris.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import core.tetris.Direction;
import core.tetris.TetrisGame;

public class AllGames {

    private final List<TetrisGame> games = new ArrayList<>();

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
}
