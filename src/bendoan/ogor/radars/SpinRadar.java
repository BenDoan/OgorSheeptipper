package bendoan.ogor.radars;

import robocode.AdvancedRobot;

public class SpinRadar extends Radar {

    public SpinRadar(AdvancedRobot robot) {
        super(robot);

        // Let the radar spin independently of the gun
        robot.setAdjustRadarForGunTurn(true);

        robot.setTurnRadarRight(Double.POSITIVE_INFINITY);

    }
}

//

