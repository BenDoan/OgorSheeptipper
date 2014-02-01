package bendoan.ogor.intel;

import java.awt.geom.*;

import robocode.util.Utils;

public class WaveBullet {
    public double startX;
    public double startY;
    public double startBearing;
    public double power;
    public long fireTime;
    public int direction;
    public int[] returnSegment;

    public WaveBullet(double x, double y, double bearing, double power, int direction, long time, int[] segment){
        startX = x;
        startY = y;
        startBearing = bearing;
        this.power = power;
        this.direction = direction;
        fireTime = time;
        returnSegment = segment;
    }

    public double getBulletSpeed(){
        return 20 - this.power * 3;
    }

    public double maxEscapeAngle(){
        return Math.asin(8 / 11);
    }

    public boolean checkHit(double enemyX, double enemyY, long currentTime){
        if (Point2D.distance(startX, startY, enemyX, enemyY) <= (currentTime - fireTime) * getBulletSpeed()){
            double desiredDirection = Math.atan2(enemyX - startX, enemyY - startY);
            double angleOffset = Utils.normalRelativeAngle(desiredDirection - startBearing);
            double guessFactor = Math.max(-1, Math.min(1, angleOffset / maxEscapeAngle())) * direction;
            int index = (int) Math.round((returnSegment.length - 1) / 2 * (guessFactor + 1));
            returnSegment[index]++;
            return true;
        }
        return false;
    }
}
