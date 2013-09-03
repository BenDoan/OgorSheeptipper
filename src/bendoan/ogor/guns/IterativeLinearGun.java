package bendoan.ogor.guns;

import java.awt.Color;
import java.awt.geom.Point2D;

import bendoan.ogor.utils.MathUtils;
import bendoan.ogor.utils.Util;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

/**
 * Implements a gun that fires at an enemy's last known location
 */
public class IterativeLinearGun extends Gun {
	@SuppressWarnings("unused")
	private boolean debug = false;

	public IterativeLinearGun(AdvancedRobot robot) {
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
		Point2D.Double targetCoord = MathUtils.getLocation(robot,
				event.getBearingRadians(), event.getDistance());

		Util.paintCircle(robot, targetCoord.x, targetCoord.y, 20, Color.BLUE);
	}
}
