package def;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pool
{
    Ellipse2D ellipse2D;
    private final int xPos;
    private final int yPos;

    Pool(double xCord, double yCord, double size, int i, int j)
    {
        ellipse2D = new Ellipse2D.Double(xCord, yCord, size, size);
        xPos = j;
        yPos = i;
    }

    public int getxPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }
}
