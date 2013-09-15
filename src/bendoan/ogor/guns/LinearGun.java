package bendoan.ogor.guns;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.targetselectors.NearestNeighborSelector;
import bendoan.ogor.utils.MathUtils;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class LinearGun extends Gun {

    private boolean debug = false;
    public int range = 400;
    private double bulletPower = 3;
    public double distanceToTarget = 0;

    public LinearGun(AdvancedRobot robot) {
        super(robot);
        robot.setAdjustGunForRobotTurn(true);
    }

    @Override
    public void onEvent(Event event) {
        // If this event happens to be a ScannedRobotEvent...
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event);
        }
    }

    private void onScannedRobot(ScannedRobotEvent event) {
        Point2D.Double location = MathUtils.getLocation(robot,
                event.getBearingRadians(), event.getDistance());
        NumberFormat formatter = new DecimalFormat("#0.00");

        fireAt(event);

        if (debug) {
            paintLoc(robot, location.x, location.y, 20);
        }

        if (debug) {
            System.out.println("\n\nThe Target's Coordinates are: ("
                    + formatter.format(location.x) + ", "
                    + formatter.format(location.y) + ")");
        }
    }

    private void fireAt(ScannedRobotEvent event) {
        if (event.getName().equals(Observer.TARGET)) {
            if (debug) {
                System.out.println("Shooting at: " + event.getName());
            }

            distanceToTarget = event.getDistance();

            if (NearestNeighborSelector.disabledBotExists()) {
                this.bulletPower = Rules.MIN_BULLET_POWER;
            } else if (event.getDistance() < this.range) {
                this.bulletPower = Rules.MAX_BULLET_POWER;
            } else {
                this.bulletPower = Rules.MIN_BULLET_POWER;
            }

            double absoluteBearing = robot.getHeadingRadians()
                    + event.getBearingRadians();

            double BulletFudgeFactor = this.bulletPower * 3;

            robot.setTurnGunRightRadians(Utils
                    .normalRelativeAngle(absoluteBearing
                    - robot.getGunHeadingRadians()
                    + (event.getVelocity()
                    * Math.sin(event.getHeadingRadians()
                    - absoluteBearing) / BulletFudgeFactor)));

            robot.setFire(this.bulletPower);
        }
    }

    public static void paintLoc(AdvancedRobot robot, double x1, double y1,
            double r1) {
        int x = (int) x1;
        int y = (int) y1;
        int r = (int) r1;

        Graphics2D g = robot.getGraphics();
        g.setColor(Color.green);
        g.setStroke(new BasicStroke(6));
        g.drawOval(x - r, y - r, r * 2, r * 2);
    }
}
