package bendoan.ogor.targetselectors;

import bendoan.ogor.intel.Observer;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class TargetSelector {

    protected AdvancedRobot robot;

    public TargetSelector(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void act() {
        // Do nothing
    }

    public void onEvent(Event event) {
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event);
        } else if (event instanceof RobotDeathEvent) {
            onRobotDeath((RobotDeathEvent) event);
        }
    }

    public void onRobotDeath(RobotDeathEvent event) {
        // If the robot that has died was our target, remove it as the target
        if (event.getName().equals(Observer.TARGET)) {
            Observer.TARGET = null;
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        // If we currently have no target, target the scanned robot
        if (Observer.TARGET == null) {
            Observer.TARGET = event.getName();
        }
    }
}
