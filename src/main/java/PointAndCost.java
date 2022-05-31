import java.awt.*;
import java.util.Objects;

public class PointAndCost {
    Point point;
    Integer cost;

    public PointAndCost(Point point, Integer cost) {
        this.point = point;
        this.cost = cost;
    }

    public Point getPoint() {
        return point;
    }

    public Integer getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointAndCost that = (PointAndCost) o;
        return Objects.equals(point, that.point) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, cost);
    }
}
