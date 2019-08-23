package sample;

import org.junit.Test;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestOctTree {






    @Test
    public void testParticleCount() {
        OctTree octTree = new OctTree(new Octant(0, 0, 0, 100));
        List<Particle> pList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Particle p = new Particle(i, i, i, i, i, i, i, i);
            octTree.insert(p);
        }
        assertEquals(octTree.particleCounts, 100);
    }

    @Test
    public void testInsert() {
        OctTree octTree = new OctTree(new Octant(0, 0, 0, 2));
       // List<Particle> pList = new ArrayList<>();
        Particle p1 = new Particle(0, 0, 0, 0, 0, 0, 0, 0);
        Particle p2 = new Particle(1, 1, 1, 1, 1, 1, 1, 1);

        octTree.insert(p1);
        octTree.insert(p2);

    }

}
