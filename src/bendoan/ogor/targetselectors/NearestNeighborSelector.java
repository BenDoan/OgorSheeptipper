package bendoan.ogor.targetselectors;

import java.util.ArrayList;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.intel.RobotIntel;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

/**
 * Implements a target selector that chooses the nearest enemy as its target
 */
public class NearestNeighborSelector extends TargetSelector {
	private boolean debug = false;
	private static boolean disabledRobot = false;

	public NearestNeighborSelector(AdvancedRobot robot) {
		super(robot);
	}

	public void onEvent(Event event) {
		// If this event happens to be a ScannedRobotEvent...
		if (event instanceof ScannedRobotEvent) {
			onScannedRobot((ScannedRobotEvent) event);
		}
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		Observer.TARGET = getClosestRobotName();

	}

	/**
	 * Looks through all intel and chooses closest robot
	 * 
	 * @return Name of closest robot
	 */
	private String getClosestRobotName() {
		double closestDistance = Double.MAX_VALUE;
		int closestIndex = -1;
		disabledRobot = false;
		ArrayList<RobotIntel> intel = Observer.getAllIntel();

		for (int i = 0; i < intel.size(); i++) {

			if (intel.get(i).getEnergy() == 0) {
				closestIndex = i;
				disabledRobot = true;
				return intel.get(closestIndex).getName();
			}

			if (intel.get(i).getDistance() < closestDistance) {
				closestDistance = intel.get(i).getDistance();
				closestIndex = i;
			}
		}

		if (closestIndex == -1) {
			if (debug) {
				System.out.println("The closest index is null");
			}
			return null;
		} else {
			if (debug) {
				System.out.println("Target is: "
						+ intel.get(closestIndex).getName());
			}

			return intel.get(closestIndex).getName();
		}

	}

	public static boolean getDisabledStatus() {
		return disabledRobot;
	}

	@Override
	public void act() {

	}

}
