package bendoan.ogor.intel;

import java.util.ArrayList;

import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Observer {

    private static boolean debug = false;
    private static ArrayList<RobotIntel> intel = new ArrayList<>();
    public static String TARGET = null; // current target
    private static ArrayList<RobotIntel> log = new ArrayList<>();

    public static void remember(ScannedRobotEvent event) {
        int index = getIndexOfRobot(event.getName());

        if (index != -1) {
            intel.set(index, new RobotIntel(event));
        } else {
            intel.add(new RobotIntel(event));
        }

    }

    public static RobotIntel forget(String name) {
        int index = getIndexOfRobot(name);

        if (index != -1) {
            return intel.remove(index);

        } else {
            return null;
        }

    }

    public static ArrayList<RobotIntel> getAllIntel() {
        return intel;
    }

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

    public static void onScannedRobot(ScannedRobotEvent event) {
        remember(event);
    }

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

    public static void reset() {
        intel.clear();
        if (debug) {
            System.out.println("Clearing all intel");
        }
    }
}
