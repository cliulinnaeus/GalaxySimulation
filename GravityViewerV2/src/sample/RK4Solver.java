package sample;

public class RK4Solver implements Solver {
    @Override
    public void updatePosAndVelo(Particle p, double dt) {
        double k1x = p.fx / p.mass * dt;
        double k1y = p.fy / p.mass * dt;
        double k1z = p.fz / p.mass * dt;

//        double k2x = p.
    }
}
