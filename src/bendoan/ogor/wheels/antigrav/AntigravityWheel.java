package bendoan.ogor.wheels.antigrav;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import bendoan.ogor.intel.Observer;
import bendoan.ogor.intel.RobotIntel;
import bendoan.ogor.utils.MathUtils;
import bendoan.ogor.utils.Util;
import bendoan.ogor.wheels.Wheel;
import robocode.AdvancedRobot;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.util.Utils;

public class AntigravityWheel extends Wheel {

    private boolean debug = true;
    int bulletHits = 0;
    int distToWall = 200;

    public AntigravityWheel(AdvancedRobot robot) {
        super(robot);
    }

    @Override
    public void onEvent(Event event) {
        // If this event happens to be a ScannedRobotEvent...
        if (event instanceof HitByBulletEvent) {
            onHitByBullet((HitByBulletEvent) event);
        }
    }

    private void onHitByBullet(HitByBulletEvent event) {
        bulletHits++;
        if (bulletHits == 1) {
            //forces.add(new RadialForce(robot.getX(), robot.getY()));
            bulletHits = 0;
        }
    }

    @Override
    public void act() {
        ArrayList<RadialForce> forces = getRobotForces();

        Point2D.Double vector = getTotalForceVector(forces);

        double turnAngle = getTurnAngle(vector);

        if (Math.abs(getTurnAngle(vector)) > (Math.PI / 2)) {// go backwards
            robot.setAhead(-1000);
            robot.setTurnRightRadians(-Utils.normalRelativeAngle(turnAngle));
        } else {//go forwards
            robot.setAhead(1000);
            robot.setTurnRightRadians(Utils.normalRelativeAngle(turnAngle));
        }
    }

    private double getTurnAngle(Double vector) {
        return MathUtils.getHeading(robot.getX(), robot.getY(), vector.x,
                vector.y) - robot.getHeadingRadians();
    }

    // combines the RadialForces to find the optimum location to go to
    private Point2D.Double getTotalForceVector(ArrayList<RadialForce> forces) {
        Point2D.Double totalVector = new Point2D.Double();

        for (RadialForce force : forces) {
            Util.paintCircle(robot, force.getX(), force.getY(), 5, Color.magenta);

            double heading = MathUtils.getHeading(force.getX(), force.getY(),
                    robot.getX(), robot.getY());

            double eforce = force.getEffectiveForce(robot);

            double x = MathUtils.computeXComponent(eforce, heading);
            double y = MathUtils.computeYComponent(eforce, heading);

            totalVector.x += x;
            totalVector.y += y;
        }

        Util.paintLine(robot, totalVector.getX(), totalVector.getY(),
                Color.blue);

        return totalVector;
    }

    private ArrayList<RadialForce> getRobotForces() {
        HashMap<String, ArrayList<RobotIntel>> intel = Observer.getLog();
        ArrayList<RadialForce> forces = new ArrayList<RadialForce>();

        // creates a radialforce object for each robot and adds them to forces
        for (String key : intel.keySet()) {
            RobotIntel r = intel.get(key).get(intel.get(key).size()-1);

            Point2D.Double location = MathUtils.getLocation(robot, r.getBearingRadians(), r.getDistance());
            RadialForce rf = new RadialForce(location.x, location.y);
            forces.add(rf);
        }

        Point2D.Double wallR = new Double();
        Point2D.Double wallL = new Double();
        Point2D.Double wallU = new Double();
        Point2D.Double wallD = new Double();
        Point2D.Double wallM = new Double();

        // lower wall grav point
        wallD.x = robot.getX();
        wallD.y = 0;
        if (robot.getY() < distToWall) {
            forces.add(new RadialForce(wallD.x, wallD.y));
        }

        // right wall grav point
        wallR.y = robot.getY();
        wallR.x = robot.getBattleFieldWidth();
        if ((robot.getBattleFieldWidth() - robot.getX()) < distToWall) {
            forces.add(new RadialForce(wallR.x, wallR.y));
        }

        // upper wall grav point
        wallU.y = robot.getBattleFieldHeight();
        wallU.x = robot.getX();
        if ((robot.getBattleFieldHeight() - robot.getY()) < distToWall) {
            forces.add(new RadialForce(wallU.x, wallU.y));
        }

        // left wall grav point
        wallL.y = robot.getY();
        wallL.x = 0;
        if (robot.getX() < distToWall) {
            forces.add(new RadialForce(wallL.x, wallL.y));
        }

        // middle grav point
        wallM.y = robot.getBattleFieldHeight() / 2;
        wallM.x = robot.getBattleFieldWidth() / 2;
        forces.add(new RadialForce(wallM.x, wallM.y));

        return forces;
    }

}
