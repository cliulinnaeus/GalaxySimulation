package sample;

public class Particle {

    protected double mass;
    protected double radius; //in 10^7 m
    protected double rx, ry, rz;
    protected double vx, vy, vz;
    protected double fx, fy, fz;
    public final static double G = 6.67E-11;


    public Particle(double rx, double ry, double rz,
                    double vx, double vy, double vz, double mass, double radius) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.mass = mass;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        fx = 0;
        fy = 0;
        fz = 0;
    }

    public boolean in (Octant o) {
        return o.contains(rx, ry, rz);
    }

    // create equivalent mass
    public static Particle add (Particle a, Particle b) {
        double totMass = a.mass + b.mass;
        double rx = (a.rx * a.mass + b.rx * b.mass) / totMass;
        double ry = (a.ry * a.mass + b.ry * b.mass) / totMass;
        double rz = (a.rz * a.mass + b.rz * b.mass) / totMass;
        double vx = (a.vx * a.mass + b.vx * b.mass) / totMass;
        double vy = (a.vy * a.mass + b.vy * b.mass) / totMass;
        double vz = (a.vz * a.mass + b.vz * b.mass) / totMass;
        // radius can be arbitrary since this is an equivalent paticle.
        return new Particle(rx, ry, rz, vx, vy, vz, totMass, 0);
    }

    public void addForce (Particle b) {
        // G * m1 * m2 / r^3 * r
        double force = G * b.mass * mass / (distance(this, b) * distance(this, b) * distance(this, b));
        fx = fx + force * (b.rx - rx);
        fy = fy + force * (b.ry - ry);
        fz = fz + force * (b.rz - rz);
    }

    public static double distance (Particle a, Particle b) {
        double dx = a.rx - b.rx;
        double dy = a.ry - b.ry;
        double dz = a.rz - b.rz;

        return Math.sqrt(dx * dx + dy * dy + dz *dz);

    }

    //TODO:
    // if the distance is smaller than the sum of the radius, merge the particles
    public void merge (Particle b) {

    }


    public void updatePosAndVelo (Solver solver, double dt) {
       solver.updatePosAndVelo(this, dt);
    }

    public void clearForce() {
        fx = 0;
        fy = 0;
        fz = 0;
    }

}
