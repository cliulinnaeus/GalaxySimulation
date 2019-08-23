package sample;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
public class TestOctant {

    @Test
    public void testContains() {

        Octant octant = new Octant(0, 0, 0, 10);

        boolean leftBottomFront = octant.contains(0, 0, 0);
        boolean rightTopBack = octant.contains(-10, 10, 10);
        boolean middle = octant.contains(-5, 5, 5);
        boolean frontRight = octant.contains(0, 10, 0);
        boolean leftBack = octant.contains(-10, 0, 0);

        assertEquals(true, leftBottomFront);
        assertEquals(false, rightTopBack);
        assertEquals(true, middle);
        assertEquals(false, frontRight);
        assertEquals(false, leftBack);
    }

    @Test
    public void testRandomContains() {
        Octant octant = new Octant(0, 0, 0, 10);

        Random random = new Random();
        boolean[] results = new boolean[1000];
        for (int i = 0; i < 1000; i++) {
            double rx = - random.nextDouble() * 10;
            double ry = random.nextDouble() * 10;
            double rz = random.nextDouble() * 10;
            results[i] = octant.contains(rx, ry, rz);
        }

        for (boolean b : results) {
            assertTrue(b);
        }

    }

    @Test
    public void testSubTree() {
        Octant octant = new Octant(0, 0, 0, 10);

        Octant I = octant.I();
        Octant II = octant.II();
        Octant III = octant.III();
        Octant IV = octant.IV();
        Octant V = octant.V();
        Octant VI = octant.VI();
        Octant VII = octant.VII();
        Octant VIII = octant.VIII();


        // test length
        Random random = new Random();
        boolean[] resultsI = new boolean[1000];
        for (int i = 0; i < 1000; i++) {
            double rx = -random.nextDouble() * 5;
            double ry = random.nextDouble() * 5 + 5;
            double rz = random.nextDouble() * 5 + 5;

            resultsI[i] = I.contains(rx, ry, rz);

        }

        for (boolean b: resultsI) {
            assertTrue(b);
        }

//        for (Octant o : octant.makeOctantList()) {
//
//
//
//
//            assertEquals(5.0, o.length(), 0.0);
//        }



    }



}
