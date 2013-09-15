package bendoan.ogor.wheels.antigrav;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

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

    private boolean debug = false;
    int bulletHits = 0;
    int constMoving = 1;
    double constMovingX = 99999;
    double constMovingY = 99999;

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
        if (bulletHits == 3) {
        }
    }

    @Override
    public void act() {
        // get all robot forces
        ArrayList<RadialForce> forces = getRobotForces();

        // get total x- and y- forces
        Point2D.Double vector = getTotalForceVector(forces);
        // Calculate turn angle according to x-- and y forces
        double turnAngle = getTurnAngle(vector);
        // turn the robot
        if (debug) {
            System.out.println("The turn angle is: "
                    + Math.toDegrees(turnAngle));
        }

        if (debug) {
            System.out.println(getTurnAngle(vector));
        }
        if (Math.abs(getTurnAngle(vector)) > (Math.PI / 2)) {
            if (debug) {
                System.out.println("BACKWARDS");
            }
            robot.setAhead(-1000);
            robot.setTurnRightRadians(-Utils.normalRelativeAngle(turnAngle));
        } else {
            if (debug) {
                System.out.println("FORWARDS");
            }
            robot.setTurnRightRadians(Utils.normalRelativeAngle(turnAngle));
            robot.setAhead(1000);
        }

        constMoving++;
    }

    private double getTurnAngle(Double vector) {
        return MathUtils.getHeading(robot.getX(), robot.getY(), vector.x,
                vector.y) - robot.getHeadingRadians();
    }

    // get heading of vector from enemy to our robot
    // get effective force of Radialforce
    // calculate x- and y- components using heading and effective force
    // add components to total points.2d
    private Point2D.Double getTotalForceVector(ArrayList<RadialForce> forces) {
        Point2D.Double totalVector = new Point2D.Double();

        if (debug && forces.size() > 2) {
            System.out.println("Antigravity:x,y = (" + forces.get(2).getX()
                    + ", " + forces.get(2).getY() + ")");

            System.out.println("Items in array forces" + forces);
        }

        for (RadialForce force : forces) {
            Util.paintCircle(robot, force.getX(), force.getY(), 5,
                    Color.magenta);

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

        if (debug) {
            System.out.println("\n\n\n\n\n\n\n\nAntigravity:Location = ("
                    + totalVector.getX() + ", " + totalVector.getY() + ")");

            Util.paintCircle(robot, totalVector.getX(), totalVector.getY(), 20,
                    Color.blue);
        }

        return totalVector;
    }

    private ArrayList<RadialForce> getRobotForces() {
        ArrayList<RobotIntel> intel = Observer.getAllIntel();
        ArrayList<RadialForce> forces = new ArrayList<RadialForce>();

        Point2D.Double wallR = new Double();
        Point2D.Double wallL = new Double();
        Point2D.Double wallU = new Double();
        Point2D.Double wallD = new Double();
        // Point2D.Double wallM = new Double();


        for (RobotIntel r : intel) {
            Point2D.Double location = MathUtils.getLocation(robot,
                    r.getBearingRadians(), r.getDistance());
            RadialForce rf = new RadialForce(location.x, location.y);

            forces.add(rf);

        }

        // lower wall grav point
        wallD.x = robot.getX();
        wallD.y = 0;
        if (robot.getY() < 220) {
            forces.add(new RadialForce(wallD.x, wallD.y));
        }

        // right wall grav point
        wallR.y = robot.getY();
        wallR.x = robot.getBattleFieldWidth();
        if ((robot.getBattleFieldWidth() - robot.getX()) < 220) {
            forces.add(new RadialForce(wallR.x, wallR.y));
        }

        // upper wall grav point
        wallU.y = robot.getBattleFieldHeight();
        wallU.x = robot.getX();
        if ((robot.getBattleFieldHeight() - robot.getY()) < 220) {
            forces.add(new RadialForce(wallU.x, wallU.y));
        }

        // left wall grav point
        wallL.y = robot.getY();
        wallL.x = 0;
        if (robot.getX() < 220) {
            forces.add(new RadialForce(wallL.x, wallL.y));
        }

        // prevents the robot from standing still
        if (robot.getOthers() > 2) {
            if (constMoving % 999999999 == 0) { // 35
                constMovingX = robot.getX();
                constMovingY = robot.getY();
            }
        } else {
            if (constMoving % 100 == 0) { // 35
                constMovingX = robot.getX();
                constMovingY = robot.getY();
            }
        }

        if (constMovingX != 99999) {
            forces.add(new RadialForce(constMovingX, constMovingY));
        }

        return forces;
    }
}
