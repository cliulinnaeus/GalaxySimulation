package sample;

import org.junit.Test;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestOctTree {


    OctTree octTree = new OctTree(new Octant(0, 0, 0, 100));
    List<Particle> pList = new ArrayList<>();



    @Test
    public void testParticleCount() {
        for (int i = 0; i < 100; i++) {
            Particle p = new Particle(i, i, i, i, i, i, i, i);
            octTree.insert(p);
        }
        assertEquals(octTree.particleCounts, 100);
    }

    @Test
    public void testInsert() {

    }

}
