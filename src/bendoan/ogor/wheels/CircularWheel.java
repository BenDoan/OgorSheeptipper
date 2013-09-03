package bendoan.ogor.wheels;

import robocode.AdvancedRobot;
import robocode.Event;

/**
 * Implements a wheel that moves the robot like a ping pong ball, simply
 * bouncing off walls & robots
 */
public class CircularWheel extends Wheel {

	public CircularWheel(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void act() {
		robot.setAhead(Double.MAX_VALUE);
		robot.setTurnLeft(100);
	}

	@Override
	public void onEvent(Event event) {
		// If the event is a ScannedRobotEvent or HitWallEvent, call the
		// the appropriate method
	}
}
