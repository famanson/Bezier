package src;

import java.awt.*;
import java.awt.geom.Line2D;

public class MyGraphics {

    public boolean isFill;
    public Color paintColor, bkColor;
    public double scale;
    public int pointRadius;
    public double worldX;
    public double worldY;
    public int screenX;
    public int screenY;
    public MyPolygon thePoly;

    public MyGraphics(Color paint, Color bk)
    {
        scale = 1.0D;
        pointRadius = 4;
	thePoly = new MyPolygon();
	paintColor = paint;
	bkColor = bk;
    }


    public void clear(Component component, Color paramColor)
    {
	thePoly = new MyPolygon();
	component.repaint();
    }

    private void drawPoint(Graphics g, int x, int y)
    {
	int i = x;
        int j = y;
        int k = pointRadius;
	Color color = g.getColor();
	g.setColor(paintColor);
	g.fillOval(i - k, j - k, k + k, k + k);
	g.setColor(color);
    }

    public void addPolyPoint(Graphics g, int x, int y)
    {
	if (thePoly.npoints>0) {
	    g.drawLine(thePoly.xpoints[thePoly.npoints-1],
		       thePoly.ypoints[thePoly.npoints-1],
		       x,y);
	}
	thePoly.addPoint(x,y);
	drawPoint(g, x, y);

	//	System.out.println("...addPoly..."+thePoly.npoints);
    }

    private void drawPolyPoints(Graphics g)
    {
	//System.out.println("...drawPoly..."+thePoly.npoints);
	for (int i=0; i<thePoly.npoints; i++) {
	    drawPoint(g, thePoly.xpoints[i], thePoly.ypoints[i]);
	}
    }

    public void redrawThePolygon(Graphics g)
    {
	Color color = g.getColor();
	g.setColor(paintColor);
	drawPolyPoints(g);
	g.drawPolyline(thePoly.xpoints,thePoly.ypoints,thePoly.npoints);
	if (thePoly.elevated) {
	    g.setColor(bkColor);
	    g.drawPolyline(thePoly.Elevated.xpoints,
			   thePoly.Elevated.ypoints,
			   thePoly.Elevated.npoints
			   );
	}
	g.setColor(color);
    }
 
    public void elevateOnce(Graphics g) 
    {
	thePoly.elevateOnce();
	Color color = g.getColor();
	g.setColor(paintColor);
	drawPolyPoints(g);
	g.drawPolyline(thePoly.xpoints,thePoly.ypoints,thePoly.npoints);
	g.setColor(bkColor);
	g.drawPolyline(thePoly.Elevated.xpoints,
		       thePoly.Elevated.ypoints,
		       thePoly.Elevated.npoints
		       );
	g.setColor(color);
    }
}



