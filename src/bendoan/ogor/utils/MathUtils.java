package bendoan.ogor.utils;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.util.Utils;

public class MathUtils {

	/**
	 * Returns the heading from x1,y1 to x2,y2. The returned value is in the
	 * range 0..PI*2.
	 * 
	 * @param x1
	 *            The "from" x position.
	 * @param y1
	 *            The "from" y position.
	 * @param x2
	 *            The "to" x position.
	 * @param y2
	 *            The "to" y position.
	 */
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

	/**
	 * Returns an angular velocity given headings and times. It is assumed that
	 * the two headings are within PI of each other and the returned angular
	 * velocity reflects this assumption.
	 */
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

	/**
	 * Computes the bearing between two angles.
	 * 
	 * @return A value between -PI and PI
	 */
	public static double getBearing(double from, double to) {
		double ret = Utils.normalAbsoluteAngle(to - from);
		while (ret > Math.PI) {
			ret -= Math.PI * 2;
		}
		return ret;
	}

	/**
	 * Returns an angle between -PI and PI that is equivalent to the given
	 * angle.
	 * 
	 * @param ang
	 *            An angle in radians.
	 */
	public static double normalizeBearing(double ang) {
		while (ang > Math.PI) {
			ang -= 2 * Math.PI;
		}
		while (ang < -Math.PI) {
			ang += 2 * Math.PI;
		}
		return ang;
	}

	/**
	 */
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	/**
	 * 
	 * @param distance
	 *            The distance the bullet must travel.
	 * @param firepower
	 *            The power of the bullet
	 * @return The number of ticks it will take for the bullet to travel the
	 *         specified distance.
	 */
	public static double getBulletFlightTime(double distance, double firepower) {
		return (20 - (3 * firepower)) * distance;
	}

	/**
	 * 
	 * @param time
	 *            The amount of time that and object will be traveling.
	 * @param velocity
	 *            The velocity at which and object will be traveling.
	 * @return The distance that an object will travel in the given amount of
	 *         time at the given velocity.
	 */
	public static double getDistanceTraveled(double time, double velocity) {
		return time * velocity;
	}

	/**
	 * 
	 * @param distance
	 *            The length of a vector.
	 * @param headingRadians
	 *            The heading of a vector.
	 * @return The component of the given distance and heading in the
	 *         x-direction.
	 */
	public static double computeXComponent(double distance,
			double headingRadians) {
		return distance * Math.sin(headingRadians);
	}

	/**
	 * 
	 * @param distance
	 *            The length of a vector.
	 * @param headingRadians
	 *            The heading of a vector.
	 * @return The component of the given distance and heading in the
	 *         y-direction.
	 */
	public static double computeYComponent(double distance,
			double headingRadians) {
		return distance * Math.cos(headingRadians);
	}

	// calculates location of an object at the given bearing and given distance
	// bearing and distance of object
	public static Point2D.Double getLocation(AdvancedRobot robot,
			double bearing, double distance) {
		double headingToCurr = robot.getHeadingRadians() + bearing;

		double x = robot.getX() + computeXComponent(distance, headingToCurr);
		double y = robot.getY() + computeYComponent(distance, headingToCurr);

		Point2D.Double location = new Point2D.Double(x, y);
		return location;
	}
}