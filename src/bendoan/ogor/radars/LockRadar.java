package bendoan.ogor.radars;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

public class LockRadar extends Radar {

    public LockRadar(AdvancedRobot robot) {
        super(robot);

        // Let the radar spin independently of the gun
        robot.setAdjustRadarForGunTurn(true);

        robot.setTurnRadarRight(Double.POSITIVE_INFINITY);

    }

    public void onEvent(Event event) {
        if (event instanceof ScannedRobotEvent) {
            OnScanRobot((ScannedRobotEvent) event);
        }
    }

    private void OnScanRobot(ScannedRobotEvent event) {
        robot.setTurnRadarLeftRadians(robot.getRadarTurnRemaining());
    }
}
