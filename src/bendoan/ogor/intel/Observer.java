package bendoan.ogor.intel;

import java.util.ArrayList;

import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Observer {
	private static boolean debug = false;
	/** List of all robot intel */
	private static ArrayList<RobotIntel> intel = new ArrayList<RobotIntel>();

	/** Name of robot we're currently targeting */
	public static String TARGET = null;

	/**
	 * Stores scan data into list of all intel
	 * 
	 * @param event
	 *            Event containing scan details
	 */
	public static void remember(ScannedRobotEvent event) {
		// Get the index of the scanned robot's intel
		int index = getIndexOfRobot(event.getName());

		// If the scanned robot has already been stored, then replace it.
		// Otherwise, add it to the list of intel.
		if (index != -1) {
			intel.set(index, new RobotIntel(event));
		} else {
			intel.add(new RobotIntel(event));
		}

	}

	/**
	 * "Forgets" a robot by removing it's associated intel from the list
	 * 
	 * @param name
	 *            Name of robot to forget
	 * @return RobotIntel object that was removed from the list
	 */
	public static RobotIntel forget(String name) {
		// Examine all elements of the ArrayList, looking for the robot
		// with the given name.
		// When found, remove the RobotIntel object and return it.
		// In the case that all elements have been examined, without finding the
		// given robot name, return null.
		int index = getIndexOfRobot(name);

		if (index != -1) {
			return intel.remove(index);

		} else {
			return null;
		}

	}

	/**
	 * @return List of all RobotIntel objects
	 */
	public static ArrayList<RobotIntel> getAllIntel() {
		return intel;
	}

	/**
	 * Gets the index of the given Robot name, or -1 if not present
	 * 
	 * @param name
	 *            Name of robot to search for
	 * @return Index of given robot, or -1 if not found
	 */
	public static int getIndexOfRobot(String name) {
		// Examine all elements of the ArrayList, looking for the robot
		// with the given name.
		// When found, return the index.
		// In the case that all elements have been examined, without finding the
		// given robot name, return -1.
		for (int i = 0; i < intel.size(); i++) {
			if (intel.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * When a robot is scanned, store the intel
	 * 
	 * @param event
	 *            Event containing scan details
	 */
	public static void onScannedRobot(ScannedRobotEvent event) {
		// Store the scan data in our intel
		remember(event);
	}

	/**
	 * When a robot dies... ??
	 * 
	 * @param event
	 *            Event containing robot death details
	 */
	public static void onRobotDeath(RobotDeathEvent event) {
		forget(event.getName());
		if (debug) {
			System.out.println("===========Reseting Intel");
		}
	}

	public static void onEvent(Event event) {
		if (event instanceof ScannedRobotEvent) {
			onScannedRobot((ScannedRobotEvent) event);
		} else if (event instanceof RobotDeathEvent) {
			onRobotDeath((RobotDeathEvent) event);
		}
	}

	/**
	 * Removes all currently stored intel
	 */
	public static void reset() {
		// Remove all currently stored intel
		intel.clear();
		if (debug) {
			System.out.println("Clearing all intel");
		}
	}
}
