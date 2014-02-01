package bendoan.ogor.guns;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.intel.RobotIntel;
import bendoan.ogor.targetselectors.NearestNeighborSelector;
import bendoan.ogor.utils.MathUtils;
import bendoan.ogor.utils.Util;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class PatternMatchingGun extends Gun {

    private boolean debug = false;
    public int range = 400;
    private double bulletPower = 3;
    public double distanceToTarget = 0;
    ArrayList<RobotIntel> log;
    ArrayList<RobotIntel> pattern;

    public PatternMatchingGun(AdvancedRobot robot) {
        super(robot);
        robot.setAdjustGunForRobotTurn(true);
    }

    @Override
    public void onEvent(Event event) {
        // If this event happens to be a ScannedRobotEvent...
        if (event instanceof ScannedRobotEvent) {
            onScannedRobot((ScannedRobotEvent) event);
        }
    }

    private void onScannedRobot(ScannedRobotEvent event) {
        fireAt(event);
    }

    public double getBulletSpeed(double power){
        return 20 - power * 3;
    }

    private double getMatchFactor(int index){
        double matchFactor = 0;
        int j = 1;
        for (int i = index; i < index+7; i++){
            double velocityDiff = this.log.get(i).getVelocity() - this.pattern.get(j).getVelocity();

            double logHeadingDelta = this.log.get(i).getHeadingRadians() - this.log.get(i-1).getHeadingRadians();//watch for array bounds
            double patternHeadingDelta = this.log.get(i).getHeadingRadians() - this.log.get(i-1).getHeadingRadians();//watch for array bounds
            double headingDiff = logHeadingDelta - patternHeadingDelta;
            matchFactor += velocityDiff + headingDiff;
            j++;
        }
        return matchFactor;
    }

    private int getClosestMatch(){
        int lowestMatchFactorIndex = 0;
        double lowestMatchFactor = 99999999;
        for (int i = 0; i < this.log.size(); i++){
            if (getMatchFactor(i) < lowestMatchFactor)
                lowestMatchFactorIndex = i;
        }
        return lowestMatchFactorIndex;
    }

    private void fireAt(ScannedRobotEvent event) {
        log = Observer.getLog().get(Observer.TARGET);
        pattern = new ArrayList<RobotIntel>(log.subList(log.size()-8, log.size()-1));
        int closestMatch = getClosestMatch();

        double curDistance = log.get(log.size()-1).getDistance();
        double timeToTarget = curDistance / getBulletSpeed(3);
    }
}
