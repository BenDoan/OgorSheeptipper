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
import bendoan.ogor.utils.Util;
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
        fireAt(event);
    }

    private void fireAt(ScannedRobotEvent event) {
        if (event.getName().equals(Observer.TARGET)) {

            // paint target with a red circle
            Point2D.Double location = MathUtils.getLocation(robot, event.getBearingRadians(), event.getDistance());
            Util.paintCircle(robot, location.getX(), location.getY(), 30, Color.red);

            distanceToTarget = event.getDistance();


            double absoluteBearing = robot.getHeadingRadians()
                    + event.getBearingRadians();

            double BulletFudgeFactor = this.bulletPower * 3;

            robot.setTurnGunRightRadians(
                Utils.normalRelativeAngle(
                    absoluteBearing - robot.getGunHeadingRadians() + (event.getVelocity() * Math.sin(event.getHeadingRadians() - absoluteBearing) / BulletFudgeFactor)
                )
            );

            if (NearestNeighborSelector.disabledBotExists()) {
                this.bulletPower = Rules.MIN_BULLET_POWER; // set bullet power to min, and snipe disabled robot
            } else if (event.getDistance() < this.range) {
                this.bulletPower = Rules.MAX_BULLET_POWER; // set bullet power to max when target is close
            } else {
                //this.bulletPower = Rules.MIN_BULLET_POWER; // set bullet power to min when target is far
                return;
            }

            robot.setFire(this.bulletPower);
        }
    }

}
