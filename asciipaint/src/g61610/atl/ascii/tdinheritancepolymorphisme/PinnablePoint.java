package g61610.atl.ascii.tdinheritancepolymorphisme;

public class PinnablePoint extends Point {
    private boolean pinned;

    public PinnablePoint(double x, double y) {
        super(x,y);
        this.pinned = false;
    }

    public void pin() {
        pinned = true;
    }

    @Override
    public Point move(double dx, double dy) {
        if (!pinned) {
            super.move(dx,dy);
        }
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + (pinned? "pinned" : "not pinned");
    }

    public static void main(String[] args) {
        Point p = new PinnablePoint(0,0);
        System.out.println(p);
        p.move(1,1);
        PinnablePoint pp = (PinnablePoint)p;
        pp.pin();
        p.move(1,1);
        System.out.println(p);
    }
}
