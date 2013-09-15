package bendoan.ogor.wheels.antigrav;

import bendoan.ogor.utils.MathUtils;
import robocode.AdvancedRobot;

public class RadialForce {

    private double x;
    private double y;

    // double x, double y, int polarity, double decay, double strength
    public RadialForce(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getEffectiveForce(AdvancedRobot robot) {
        double distance = MathUtils.getDistance(x, y, robot.getX(),
                robot.getY());

        return 70000 / distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
