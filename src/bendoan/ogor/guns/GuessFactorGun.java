package bendoan.ogor.guns;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.intel.WaveBullet;
import bendoan.ogor.targetselectors.NearestNeighborSelector;
import bendoan.ogor.utils.MathUtils;
import bendoan.ogor.utils.Util;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class GuessFactorGun extends Gun {

    private boolean debug = false;
    public int range = 400;
    private double bulletPower = 3;
    ArrayList<WaveBullet> waves = new ArrayList<>();
    static int[] stats = new int[31]; //number of uniqe guessfactors
    int direction = 1;

    public GuessFactorGun(AdvancedRobot robot) {
        super(robot);
        robot.setAdjustGunForRobotTurn(true);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event);
        }
    }

    private void onScannedRobot(ScannedRobotEvent event) {
        fireAt(event);
    }

    private void drawWave(WaveBullet wave, ScannedRobotEvent event){
        // draw origin
        Util.paintCircle(robot, wave.startX, wave.startY, 4.0, Color.green);

        // draw bearing
        double x2 = wave.startX + event.getDistance()*Math.cos(90-wave.startBearing);
        double y2 = wave.startX + event.getDistance()*Math.sin(90-wave.startBearing);
        Util.paintLine(robot, wave.startX, wave.startY, x2, y2, Color.green);
    }

    private void fireAt(ScannedRobotEvent event) {
        double absoluteBearing = robot.getHeadingRadians() + event.getBearingRadians();
        Point2D.Double enemyLocation = MathUtils.getLocation(robot, event.getBearing(), event.getDistance());

        // remove WaveBullets that have hit
        for (int i = 0; i < waves.size(); i++){
            drawWave(waves.get(i), event);
            WaveBullet currentWave = waves.get(i);
            if (currentWave.checkHit(enemyLocation.getX(), enemyLocation.getY(), robot.getTime())){
                waves.remove(currentWave);
                i--;
            }
        }

        double power = 3;
        if (event.getVelocity() != 0){
            if (Math.sin(event.getHeadingRadians()-absoluteBearing) * event.getVelocity() < 0){
                this.direction = -1;
            }else{
                this.direction = 1;
            }
        }

        int[] currentStats = stats;
        WaveBullet newWave = new WaveBullet(robot.getX(), robot.getY(), absoluteBearing, power, direction, robot.getTime(), currentStats);

        int bestIndex = 15;
        for (int i = 0; i < stats.length; i++){
            if (currentStats[bestIndex] < currentStats[i])
                bestIndex = i;
        }

        double guessFactor = (double)(bestIndex - (stats.length - 1) / 2) / ((stats.length -1) / 2);

        double angleOffset = direction * guessFactor * newWave.maxEscapeAngle();
        double gunAdjust = Utils.normalRelativeAngle(absoluteBearing - robot.getGunHeadingRadians() + angleOffset);
        robot.setTurnGunRightRadians(gunAdjust);

        if (event.getName().equals(Observer.TARGET)) {
            robot.setFire(3);
            waves.add(newWave);
        }
    }
}

