package def;

import java.awt.geom.Ellipse2D;

/**
 * Objects of this class imitates pools on a board, it contains 2D Ellipse to be drawn, and x,y gameboard coordinates
 * @author Elzbieta Wisniewska and Adam Chojnacki
 */
public class Pool
{
    private final Ellipse2D ellipse2D;
    private final int xPos;
    private final int yPos;

    /**
     * Basic constructor, that initializes 2D objects with given coordinates on plane, and sets x,y gameboard coordinates
     * @param xCord x coordinate on plane
     * @param yCord y coordinate on plane
     * @param size size of object to be drawn
     * @param i x coordinate on board
     * @param j y coordinate on board
     */
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
