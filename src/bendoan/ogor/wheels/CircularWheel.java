package bendoan.ogor.wheels;

import robocode.AdvancedRobot;
import robocode.Event;

public class CircularWheel extends Wheel {
	public CircularWheel(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void act() {
		robot.setAhead(Double.MAX_VALUE);
		robot.setTurnLeft(30);
	}

	@Override
	public void onEvent(Event event) {
	}
}
