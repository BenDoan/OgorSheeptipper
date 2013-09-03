package bendoan.ogor.guns;

import robocode.AdvancedRobot;
import robocode.Event;

/**
 * Provides a generic class that all actual guns will extend
 */
public class Gun {
	/** Reference to robot that owns this gun */
	protected AdvancedRobot robot;
	
	public Gun(AdvancedRobot robot) {
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
		// Do nothing
		// => Let the subclass implement if desired
	}
}