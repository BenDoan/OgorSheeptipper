package bendoan.ogor.wheels;

import robocode.*;
import bendoan.ogor.intel.Observer;

public class RamWheel extends Wheel {
    double dir = 1;

    public RamWheel(AdvancedRobot robot) {
        super(robot);
    }

    @Override
    public void act() {
        robot.setAhead(Double.MAX_VALUE);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event);
        }else if (event instanceof HitWallEvent){
            onHitWall((HitWallEvent) event);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e){
        if (e.getName() == Observer.TARGET){
            robot.setTurnRight(e.getBearing());
        }
    }

    public void onHitWall(HitWallEvent e){
        dir *= -1;
    }
}
