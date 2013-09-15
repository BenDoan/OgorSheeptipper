package bendoan.ogor.wheels;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;

/**
 * Implements a wheel that moves the robot like a ping pong ball, simply
 * bouncing off walls & robots
 */
public class RandomWheel extends Wheel {
    // private boolean debug = true;

    public RandomWheel(AdvancedRobot robot) {
        super(robot);
    }

    @Override
    public void act() {
        robot.setAhead(1000);
        if ((new Random().nextInt(3) + 1) == 1) {
            robot.turnRight((new Random().nextInt(3) + 1) * 30);
        }

        if ((new Random().nextInt(5) + 1) == 1) {
            randomColor();
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof HitWallEvent) {
            onHitWall((HitWallEvent) event);
        } else if (event instanceof HitRobotEvent) {
            onHitRobot((HitRobotEvent) event);
        }

    }

    private void onHitRobot(HitRobotEvent event) {
    }

    private void onHitWall(HitWallEvent event) {
    }

    public void randomColor() {
        int randomInt = (new Random().nextInt(6) + 1);

        switch (randomInt) {
            case 1:
                robot.setBodyColor(Color.GREEN);
                robot.setBulletColor(Color.GREEN);
                break;

            case 2:
                robot.setBodyColor(Color.GREEN);
                robot.setBulletColor(Color.GREEN);
                break;

            case 3:
                robot.setBodyColor(Color.MAGENTA);
                robot.setBulletColor(Color.MAGENTA);
                break;

            case 4:
                robot.setBodyColor(Color.YELLOW);
                robot.setBulletColor(Color.YELLOW);
                break;

            case 5:
                robot.setBodyColor(Color.YELLOW);
                robot.setBulletColor(Color.YELLOW);
                break;

            case 6:
                robot.setBodyColor(Color.RED);
                robot.setBulletColor(Color.RED);
                break;

            case 7:
                robot.setBodyColor(Color.PINK);
                robot.setBulletColor(Color.PINK);
                break;

            default:
                break;
        }
    }
}
