package bendoan.ogor.robots;

import java.awt.Color;
import java.util.Random;

import bendoan.ogor.guns.Gun;
import bendoan.ogor.guns.LinearGun;
import bendoan.ogor.intel.Observer;
import bendoan.ogor.radars.LockRadar;
import bendoan.ogor.radars.Radar;
import bendoan.ogor.radars.SpinRadar;
import bendoan.ogor.targetselectors.NearestNeighborSelector;
import bendoan.ogor.targetselectors.TargetSelector;
import bendoan.ogor.utils.Util;
import bendoan.ogor.wheels.Wheel;
import bendoan.ogor.wheels.antigrav.AntigravityWheel;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class OgorSheepTipper extends AdvancedRobot {
	private Wheel wheel;
	private Radar radar;
	private Gun gun;
	private TargetSelector targetSelector;

	/**
	 * Initialize all components of the robot and perform any customizations
	 * when the battle begins
	 */
	private void setupRobot() {
		// Initialize components with actual implementations
		wheel = new AntigravityWheel(this);
		radar = new SpinRadar(this);
		gun = new LinearGun(this);
		targetSelector = new NearestNeighborSelector(this);

		// Customizations
		setAllColors(new Color(209, 116, 90));
		setBulletColor(Color.red);

		evalParts();
	}

	/**
	 * This method is executed at the beginning of the battle. The while loop
	 * indicates what should be executed continuously throughout the battle
	 *
	 * @see robocode.Robot#run()
	 */
	public void run() {
		setupRobot();
		while (true) {
			// Tell each component to act
			wheel.act();
			radar.act();
			gun.act();
			targetSelector.act();

			miscStuff();

			execute();
		}
	}

	private void evalParts() {
		if (getOthers() == 1) {
			radar = new LockRadar(this);
			gun = new LinearGun(this);
		} else {
			radar = new SpinRadar(this);
			gun = new LinearGun(this);
		}
	}

	private void miscStuff() {
		// paints a circle around the robot indicating firing range
		Util.paintCircle(this, getX(), getY(), 400, Color.gray);
	}

	/**
	 * Called when your robot dies
	 */
	@Override
	public void onDeath(DeathEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when the battle has ended
	 */
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when one of your bullets hits another robot
	 */
	@Override
	public void onBulletHit(BulletHitEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when a bullet you fired does not hit a robot
	 */
	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when one of your bullets hits another bullet
	 */
	@Override
	public void onBulletHitBullet(BulletHitBulletEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	@Override
	public void onWin(WinEvent event) {
		setAhead(0);
		if (new Random().nextInt(3) == 2) {
			while (true) {
				setTurnRight(500);
				setTurnGunLeft(500);
				execute();
			}
		} else {
			while (true) {
				setTurnRadarLeft(1);
				execute();
			}
		}
	}

	/**
	 * Called when you are hit by a bullet
	 */
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when your robot hits another robot
	 */
	@Override
	public void onHitRobot(HitRobotEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when your robot hits the wall
	 */
	@Override
	public void onHitWall(HitWallEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when another robot dies
	 */
	@Override
	public void onRobotDeath(RobotDeathEvent event) {
		evalParts();

		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when the round ends
	 */
	@Override
	public void onRoundEnded(RoundEndedEvent event) {
		Observer.onEvent(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

	/**
	 * Called when your robot scans another robot
	 */
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		Observer.onScannedRobot(event);
		wheel.onEvent(event);
		radar.onEvent(event);
		gun.onEvent(event);
		targetSelector.onEvent(event);
	}

}
