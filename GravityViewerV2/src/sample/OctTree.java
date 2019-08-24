package sample;

import com.sun.javafx.scene.control.behavior.TwoLevelFocusBehavior;

import javax.jws.Oneway;
import javax.print.attribute.standard.Severity;
import java.util.ArrayList;
import java.util.List;

public class OctTree {

    private Particle particle;
    private Octant octant;
    private OctTree ONE;
    private OctTree TWO;
    private OctTree THREE;
    private OctTree FOUR;
    private OctTree FIVE;
    private OctTree SIX;
    private OctTree SEVEN;
    private OctTree EIGHT;
    public int particleCounts;

    // theta is the ratio of s/d, s is the width of the region, d is the distance from
    // node to the particle
    // if s/d < theta, don't do recursion
    public final static double THETA = 1000;

    public OctTree(Octant octant) {
        this.octant = octant;
        particleCounts = 0;
    }


    public void clear() {
        particleCounts = 0;
        particle = null;
        ONE = null;
        TWO = null;
        THREE = null;
        FOUR = null;
        FIVE = null;
        SIX = null;
        SEVEN = null;
        EIGHT = null;


    }


    public void insert (Particle p) {
        if (particle == null) {
            particle = p;

          //internal node:
        } else if (particleCounts > 1) {
            particle = Particle.add(particle, p);
            insertIntoSubTree(p);


        // external node:
        } else if (particleCounts == 1) {
            initSubTrees();
            insertIntoSubTree(p);
            insertIntoSubTree(particle);
            particle = Particle.add(particle, p);
        }

        particleCounts += 1;
    }


    private void initSubTrees() {
        ONE = new OctTree(octant.I());
        TWO = new OctTree(octant.II());
        THREE = new OctTree(octant.III());
        FOUR = new OctTree(octant.IV());
        FIVE = new OctTree(octant.V());
        SIX = new OctTree(octant.VI());
        SEVEN = new OctTree(octant.VII());
        EIGHT = new OctTree(octant.VIII());

    }

    private void insertIntoSubTree (Particle p) {
        for (OctTree o : makeOcttreeList()) {
            if (p.in(o.octant)) {
                o.insert(p);
                break;
            }
        }
    }



    public void updateForce (Particle p) {
        /**
         * recurse on all nodes in the tree starting from root
         * if node's s/d < theta, use node's particle.
         * else recurse on every subtree.
         */

        Particle currParticle = particle;
        if (currParticle == null) {
            return;
        }
        if (currParticle == p) {
            return;
        }
        // if external node
        if (particleCounts == 1) {
            // update p's force
            p.addForce(currParticle);
        } else {
            double indicator = octant.length() / Particle.distance(p, particle);

            // the particle is far away
            if (indicator < THETA) {
                p.addForce(currParticle);
            } else {
                // run recursion on the next octant for p

                for (OctTree octTree : makeOcttreeList()) {
                    if (octTree != null) {
                        octTree.updateForce(p);
                    }
                }
            }
        }




    }

    private List<OctTree> makeOcttreeList() {
        List<OctTree> octTreeList = new ArrayList<>();
        octTreeList.add(ONE);
        octTreeList.add(TWO);
        octTreeList.add(THREE);
        octTreeList.add(FOUR);
        octTreeList.add(FIVE);
        octTreeList.add(SIX);
        octTreeList.add(SEVEN);
        octTreeList.add(EIGHT);
        return octTreeList;
    }

}
