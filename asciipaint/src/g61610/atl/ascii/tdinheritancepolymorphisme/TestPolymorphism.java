package g61610.atl.ascii.tdinheritancepolymorphisme;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class TestPolymorphism {
    public static void main(String[] args) {
        List<Movable> points  = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new ColoredPoint(2,4,0xFF0000FF));
        points.add(new PinnablePoint(1,1));
        moveAndPrintPoints(points);
    }

    public static void moveAndPrintPoints(List<Movable> list) {
        if (list.isEmpty()) {
            return;
        }
        Movable p = list.remove(0);
        p.move(1,1);
        System.out.println(p);
        moveAndPrintPoints(list);
    }
}
