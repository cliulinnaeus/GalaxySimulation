package sample;

import java.util.List;

public class Engine {
    private OctTree particleTree;
    private Octant space;
    private List<Particle> particleList;
    int pCount;
    Solver solver;
    double dt;

    public Engine(double pivotX, double pivotY,
                  double pivotZ, double size, Solver solver, List<Particle> particleList, double dt) {

        this.pCount = 0;
        this.dt = dt;
        this.solver = solver;
        this.particleList = particleList;
        space = new Octant(pivotX, pivotY, pivotZ, size);
        particleTree = new OctTree(space);

        // how to insert particles??
        for (Particle p : particleList) {
            particleTree.insert(p);
            pCount += 1;
        }

    }


    // step foward 1 step only. computed force, pos, and velo on each particle
    // reinserted particles into the octTree.
    public void stepForward () {


        for (Particle p : particleList) {
            particleTree.updateForce(p);
            p.updatePosAndVelo(solver, dt);
            p.clearForce();
        }
        particleTree.clear();
        for (Particle p : particleList) {
            particleTree.insert(p);
        }

    }




}
