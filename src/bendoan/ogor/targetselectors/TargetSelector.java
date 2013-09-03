package bendoan.ogor.targetselectors;

import bendoan.ogor.intel.Observer;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 * Provides a generic class that all actual target selectors will extend
 */
public class TargetSelector {
	/** Reference to robot that owns this target selector */
	protected AdvancedRobot robot;
	
	public TargetSelector(AdvancedRobot robot) {
		this.robot = robot;
	}
	
	/**
	 * Called each tick of the battle
	 */
	public void act() {
		// Do nothing
		// => Let the subclass implement if desired
	}
	
	/**
	 * Called when Robocode communicates any event to the robot that owns this gun
	 * @param event Robocode event object containing the details of the actual event
	 */
	public void onEvent(Event event) {
		 if (event instanceof ScannedRobotEvent) {
	        onScannedRobot((ScannedRobotEvent)event);
	    }
	    else if (event instanceof RobotDeathEvent) {
	        onRobotDeath((RobotDeathEvent)event);
	    }
	}

	public void onRobotDeath(RobotDeathEvent event) {
		// If the robot that has died was our target, remove it as the target
		if(event.getName().equals(Observer.TARGET)) {
			Observer.TARGET = null;
		}
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		// If we currently have no target, target the scanned robot
		if(Observer.TARGET == null) {
			Observer.TARGET = event.getName();
		}
	}
}
