package bendoan.ogor.targetselectors;

import java.util.ArrayList;
import java.util.HashMap;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.intel.RobotIntel;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

public class NearestNeighborSelector extends TargetSelector {

    private boolean debug = false;
    private static boolean disabledRobotExists = false;

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

    private String getClosestRobotName() {
        double closestDistance = Double.MAX_VALUE;
        String closestRobot = "";
        disabledRobotExists = false;
        HashMap<String, ArrayList<RobotIntel>> intel = Observer.getLog();

        for (String key : intel.keySet()) {
            // change target to any disabled opponents immediately
            if (intel.get(key).get(intel.get(key).size()-1).getEnergy() == 0){
                closestRobot = key;
                disabledRobotExists = true;
                return key;
            }

            if (intel.get(key).get(intel.get(key).size()-1).getDistance() < closestDistance){
                closestDistance = intel.get(key).get(intel.get(key).size()-1).getDistance();
                closestRobot = key;
            }
        }

        if (closestRobot.equals("")) {
            if (debug)
                System.out.println("The closest index is null");
            return null;
        } else {
            if (debug)
                System.out.println("Target is: " + closestRobot);
            return closestRobot;
        }
    }

    public static boolean disabledBotExists() {
        return disabledRobotExists;
    }

    @Override
    public void act() {
    }
}
