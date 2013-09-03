package bendoan.ogor.wheels;

import java.util.Random;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class StopAndGoWheel extends Wheel {
	// private boolean debug = true;
	private double enemyEnergy = 100;
	private int direction = 1;

	public StopAndGoWheel(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void act() {

	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof HitWallEvent) {
			onHitWall((HitWallEvent) event);
		} else if (event instanceof HitRobotEvent) {
			onHitRobot((HitRobotEvent) event);
		} else if (event instanceof ScannedRobotEvent) {
			OnScanRobot((ScannedRobotEvent) event);
		} else if (event instanceof HitByBulletEvent) {
			HitByBullet((HitByBulletEvent) event);
		}

	}

	private void OnScanRobot(ScannedRobotEvent event) {
		if (robot.getDistanceRemaining() == 0.0
				&& enemyEnergy - event.getEnergy() > 0.0) {
			robot.setAhead(55 * direction);
			if (new Random().nextInt(2) == 1) {
				robot.setTurnLeft(30);
			} else {
				robot.setTurnRight(30);
			}
			System.out.println("moving ahead 40*" + direction);
		}
		enemyEnergy = event.getEnergy();
	}

	private void onHitRobot(HitRobotEvent event) {
		direction *= -1;
	}

	private void onHitWall(HitWallEvent event) {
		direction *= -1;
	}

	private void HitByBullet(HitByBulletEvent event) {

	}
}
