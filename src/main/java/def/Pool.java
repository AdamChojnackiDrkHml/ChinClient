package def;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pool
{
    Ellipse2D ellipse2D;
    int xPos;
    int yPos;
    private Color borderColor;
    private Color insideColor;
    Pool(double xCord, double yCord, double size, int i, int j)
    {
        ellipse2D = new Ellipse2D.Double(xCord, yCord, size, size);
        xPos = j;
        yPos = i;

    }

    public Color getBorderColor()
    {
        return borderColor;
    }

    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }

    public Color getInsideColor()
    {
        return insideColor;
    }

    public void setInsideColor(Color insideColor)
    {
        this.insideColor = insideColor;
        setBorderColor(insideColor);
    }
}
