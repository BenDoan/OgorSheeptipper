package bendoan.ogor.radars;

import robocode.AdvancedRobot;

/**
 * Implements a radar that continuously spins
 */
public class SpinRadar extends Radar {

	public SpinRadar(AdvancedRobot robot) {
		super(robot);

		// Let the radar spin independently of the gun
		robot.setAdjustRadarForGunTurn(true);

		// Spin the radar
		robot.setTurnRadarRight(Double.POSITIVE_INFINITY);

	}
}

// 