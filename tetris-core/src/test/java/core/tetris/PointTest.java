package core.tetris;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PointTest {

    private Point point;

    @Before
    public void before() {
        point = new Point(1, 1);
    }

    @Test
    public void testDrop() {
        final Point droppedPoint = point.drop();
        assertThat(droppedPoint.getY()).isEqualTo(2);
        assertThat(droppedPoint.getX()).isEqualTo(point.getX());
    }

    @Test
    public void testUndrop() {
        final Point undroppedPoint = point.unDrop();
        assertThat(undroppedPoint.getY()).isEqualTo(0);
        assertThat(undroppedPoint.getX()).isEqualTo(point.getX());
    }

    @Test
    public void testLeft() {
        final Point leftPoint = point.left();
        assertThat(leftPoint.getX()).isEqualTo(0);
        assertThat(leftPoint.getY()).isEqualTo(point.getY());
    }

    @Test
    public void testRight() {
        final Point rightPoint = point.right();
        assertThat(rightPoint.getX()).isEqualTo(2);
        assertThat(rightPoint.getY()).isEqualTo(point.getY());
    }
}
