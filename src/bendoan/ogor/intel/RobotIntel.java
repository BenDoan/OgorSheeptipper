package bendoan.ogor.intel;

import java.awt.geom.Point2D;

import bendoan.ogor.utils.MathUtils;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class RobotIntel {

    protected AdvancedRobot robot;
    private String name; // Robot name
    private double distance; // Last distance recorded
    private double heading; // Last heading recorded
    private double headingRadians;
    private double bearing; // Last bearing recorded
    private double bearingRadians;
    private double energy; // Last recorded energy level
    private double velocity; // Gets velocity
    private Point2D.Double location;

    public RobotIntel(String name, double distance, double heading,
            double bearing, double energy, double velocity) {
        this.name = name;
        this.distance = distance;
        this.heading = heading;
        this.bearing = bearing;
        this.energy = energy;
        this.velocity = velocity;

        this.location = MathUtils.getLocation(robot, bearing, distance);
    }

    public RobotIntel(ScannedRobotEvent event) {
        name = event.getName();
        distance = event.getDistance();
        heading = event.getHeading();
        headingRadians = event.getHeadingRadians();
        bearing = event.getBearing();
        bearingRadians = event.getBearingRadians();
        energy = event.getEnergy();
        velocity = event.getVelocity();
    }

    public String toString() {
        return name + "(" + distance + ")";
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getHeadingRadians() {
        return headingRadians;
    }

    public double getBearingRadians() {
        return bearingRadians;
    }

    public Point2D.Double getLocation() {
        return location;
    }
}
