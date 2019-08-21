package sample;

import java.util.ArrayList;
import java.util.List;

public class Octant {

    /**
     * The coordinate positive x axis points out of the page. Positive y axis points right to the page.
     * positive z axis points up.
     * pivot is the bottom left vertex closest to the viewer.
     */
    private double pivotX, pivotY, pivotZ;
    private double length;



    public Octant(double pivotX, double pivotY, double pivotZ, double length) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;
        this.length = length;

    }

    public boolean contains (double x, double y, double z) {

        boolean inX = (x < pivotX && x >= pivotX - length);
        boolean inY = (y > pivotY && y <= pivotY + length);
        boolean inZ = (z > pivotZ && z <= pivotZ + length);

        return inX && inY && inZ;
    }

    public double length() {
        return length;
    }

    public Octant I() {
        return new Octant(pivotX, pivotY + length / 2, pivotZ + length / 2, length / 2);
    }

    public Octant II() {
        return new Octant(pivotX - length / 2,
                pivotY + length / 2, pivotZ + length / 2, length / 2);
    }

    public Octant III() {
        return new Octant(pivotX - length / 2,
                pivotY, pivotZ + length / 2, length / 2);
    }

    public Octant IV() {
        return new Octant(pivotX, pivotY, pivotZ + length / 2, length / 2);
    }

    public Octant V() {
        return new Octant(pivotX, pivotY + length / 2, pivotZ, length / 2);
    }

    public Octant VI() {
        return new Octant(pivotX - length / 2, pivotY + length / 2, pivotZ, length / 2);
    }

    public Octant VII() {
        return new Octant(pivotX - length / 2, pivotY, pivotZ, length / 2);
    }

    public Octant VIII() {
        return new Octant(pivotX, pivotY, pivotZ, length / 2);
    }

    public List<Octant> makeOctantList() {
        List<Octant> octantList = new ArrayList<>();
        octantList.add(I());
        octantList.add(II());
        octantList.add(III());
        octantList.add(IV());
        octantList.add(V());
        octantList.add(VI());
        octantList.add(VII());
        octantList.add(VIII());
        return octantList;
    }
}
