package sample;

public class EulerSolver implements Solver {

    public EulerSolver() {

    }

    @Override
    public void updatePosAndVelo(Particle p, double dt) {
        p.vx = p.vx + p.fx / p.mass * dt;
        p.vy = p.vy + p.fy / p.mass * dt;
        p.vz = p.vz + p.fz / p.mass * dt;
        p.rx = p.rx + p.vx * dt;
        p.ry = p.ry + p.vy * dt;
        p.rz = p.rz + p.vz * dt;
    }
}
