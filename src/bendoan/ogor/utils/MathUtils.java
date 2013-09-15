package bendoan.ogor.utils;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.util.Utils;

public class MathUtils {

    public static double getHeading(double x1, double y1, double x2, double y2) {
        double xo = x2 - x1;
        double yo = y2 - y1;
        double h = getDistance(x1, y1, x2, y2);
        if (xo > 0 && yo >= 0) {
            return Math.asin(xo / h);
        }
        if (xo >= 0 && yo < 0) {
            return Math.PI - Math.asin(xo / h);
        }
        if (xo < 0 && yo <= 0) {
            return Math.PI + Math.asin(-xo / h);
        }
        if (xo <= 0 && yo > 0) {
            return 2.0 * Math.PI - Math.asin(-xo / h);
        }
        return 0;
    }

    public static double getAngularVelocity(double headingBefore,
            double headingAfter, long timeBefore, long timeAfter) {
        if (timeBefore >= timeAfter) {
            return 0;
        }

        if (Utils.isNear(headingBefore, headingAfter)) {
            return 0;
        }

        double ret = getBearing(headingBefore, headingAfter);
        while (ret > Math.PI) {
            ret -= Math.PI * 2;
        }

        return ret / (timeAfter - timeBefore);
    }

    public static double getBearing(double from, double to) {
        double ret = Utils.normalAbsoluteAngle(to - from);
        while (ret > Math.PI) {
            ret -= Math.PI * 2;
        }
        return ret;
    }

    public static double normalizeBearing(double ang) {
        while (ang > Math.PI) {
            ang -= 2 * Math.PI;
        }
        while (ang < -Math.PI) {
            ang += 2 * Math.PI;
        }
        return ang;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double getBulletFlightTime(double distance, double firepower) {
        return (20 - (3 * firepower)) * distance;
    }

    public static double getDistanceTraveled(double time, double velocity) {
        return time * velocity;
    }

    public static double computeXComponent(double distance, double headingRadians) {
        return distance * Math.sin(headingRadians);
    }

    public static double computeYComponent(double distance, double headingRadians) {
        return distance * Math.cos(headingRadians);
    }

    // calculates location of an object at the given bearing and given distance
    // bearing and distance of object
    public static Point2D.Double getLocation(AdvancedRobot robot, double bearing, double distance) {
        double headingToCurr = robot.getHeadingRadians() + bearing;

        double x = robot.getX() + computeXComponent(distance, headingToCurr);
        double y = robot.getY() + computeYComponent(distance, headingToCurr);

        Point2D.Double location = new Point2D.Double(x, y);
        return location;
    }
}
