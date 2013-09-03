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

/**
 * Implements a gun that fires at an enemy's last known location
 */
public class LinearGun extends Gun {
	private boolean debug = false;

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

			double absoluteBearing = robot.getHeadingRadians()
					+ event.getBearingRadians();

			robot.setTurnGunRightRadians(Utils
					.normalRelativeAngle(absoluteBearing
							- robot.getGunHeadingRadians()
							+ (event.getVelocity()
									* Math.sin(event.getHeadingRadians()
											- absoluteBearing) / 9.3)));
			if (NearestNeighborSelector.getDisabledStatus()) {
				robot.setFire(Rules.MIN_BULLET_POWER);
			} else if (event.getDistance() < 400) { // 300
				robot.setFire(3);
			}
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
