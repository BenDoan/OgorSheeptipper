package bendoan.ogor.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import robocode.AdvancedRobot;

public class Util {
	public static void paintLine(AdvancedRobot robot, double x1, double y1, double x2, double y2, Color c) {
		int x = (int) x1;
		int y = (int) y1;
		int myx = (int) x2;
		int myy = (int) y2;

		Graphics2D g = robot.getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(2));
		g.drawLine(myx, myy, x, y);
	}

	public static void paintLine(AdvancedRobot robot, double x1, double y1, Color c) {
		int x = (int) x1;
		int y = (int) y1;
		int myx = (int) robot.getX();
		int myy = (int) robot.getY();

		Graphics2D g = robot.getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(2));
		g.drawLine(myx, myy, x, y);
	}

	public static void paintCircle(AdvancedRobot robot, double x1, double y1, double r1, Color c) {
		int x = (int) x1;
		int y = (int) y1;
		int r = (int) r1;

		Graphics2D g = robot.getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(3));
		g.drawOval(x - r, y - r, r * 2, r * 2);
	}

	// Taken from: http://www.bytemycode.com/snippets/snippet/82/
	public static void drawArrow(AdvancedRobot robot, int xx, int yy) {

		Graphics2D g = robot.getGraphics();
		int x = (int) robot.getX();
		int y = (int) robot.getY();

		float arrowWidth = 10.0f;
		float theta = 0.423f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength;
		float th;
		float ta;
		float baseX, baseY;

		xPoints[0] = xx;
		yPoints[0] = yy;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - x;
		vecLine[1] = (float) yPoints[0] - y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1]
				* vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = (xPoints[0] - ta * vecLine[0]);
		baseY = (yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		g.drawLine(x, y, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
	}

}
