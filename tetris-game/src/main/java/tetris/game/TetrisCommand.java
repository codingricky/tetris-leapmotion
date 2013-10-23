package tetris.game;

import core.tetris.Direction;
import org.newdawn.slick.command.BasicCommand;

public class TetrisCommand extends BasicCommand {

    private final Direction direction;

    public TetrisCommand(Direction direction) {
        super(direction.name());
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
