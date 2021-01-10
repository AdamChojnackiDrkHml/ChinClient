package def;

import java.awt.geom.Ellipse2D;

public class Pool
{
    private final Ellipse2D ellipse2D;
    private final int xPos;
    private final int yPos;

    Pool(double xCord, double yCord, double size, int i, int j)
    {
        ellipse2D = new Ellipse2D.Double(xCord, yCord, size, size);
        xPos = j;
        yPos = i;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public Ellipse2D getEllipse2D()
    {
        return ellipse2D;
    }
}
