package bendoan.ogor.intel;

import java.util.ArrayList;
import java.util.HashMap;

import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Observer {
    private static boolean debug = false;

    private static ArrayList<RobotIntel> intel = new ArrayList<>();
    public static String TARGET = null; // current target
    private static HashMap<String, ArrayList<RobotIntel>> log = new HashMap<>();

    public static void remember(ScannedRobotEvent event) {
        if (!log.containsKey(event.getName())){
            log.put(event.getName(), new ArrayList<RobotIntel>()); //add new robot to map
        }
        log.get(event.getName()).add(new RobotIntel(event));
    }

    // removes a robot from the intel log
    public static ArrayList<RobotIntel> forget(String name) {
        if (debug)
            System.out.println("Removing " + name + " from log");
        return log.remove(name);
    }

    // returns intel list
    public static HashMap<String, ArrayList<RobotIntel>> getLog() {
        return log;
    }

    public static void onScannedRobot(ScannedRobotEvent event) {
        remember(event);
    }

    public static void onRobotDeath(RobotDeathEvent event) {
        forget(event.getName());
    }

    public static void onEvent(Event event) {
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event); //send scan event to it's method
        } else if (event instanceof RobotDeathEvent) {
            onRobotDeath((RobotDeathEvent) event); //send death event to it's method
        }
    }

    //clears intel log
    public static void reset() {
        log.clear();
        if (debug)
            System.out.println("Clearing all intel");
    }
}
