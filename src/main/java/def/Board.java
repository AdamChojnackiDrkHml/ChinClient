package def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Board extends JPanel
{
    private int[][] poolType = new int[17][17];
    ArrayList<Pool> pools = new ArrayList<>();


    Board(int Id)
    {
        choosePools();
        setPlayerNums(Id);
        setColors();
        repaint();
    }

    private void choosePools()
    {
        for(int i = 0; i < poolType.length; i++)
        {
            for(int j = 0; j < poolType.length; j++)
            {
                if(isThisValidPool(j,i))
                {
                    poolType[i][j] = 1;
                    Pool newPool;
                    if(i % 2 == 0)
                    {
                        newPool = new Pool(j * 30, i * 30, 30, i, j);
                    }
                    else
                    {
                        newPool = new Pool(j * 30 + 15, i * 30,30, i, j);
                    }
                    pools.add(newPool);
                }
                else
                {
                    poolType[i][j] = 0;
                }
            }
        }
    }

    private void setPlayerNums(int Id)
    {
        for(int i = 0; i < poolType.length; i++)
        {
            for(int j = 0; j < poolType.length; j++)
            {
                if(poolType[i][j] != 0)
                {
                    if(i > 12)
                    {
                        poolType[i][j] = 1 + Id;
                    }
                    if(i < 4)
                    {
                        poolType[i][j] = 1 + (3 + Id) % 6;
                    }
                    if(i == 4)
                    {
                        if(j < 6)
                        {
                            poolType[i][j] = 1+ (2 + Id) % 6;
                        }
                        else if(j > 10)
                        {
                            poolType[i][j] = 1+ (4 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 5)
                    {
                        if(j < 5)
                        {
                            poolType[i][j] = 1 + (2 + Id) % 6;
                        }
                        else if(j > 10)
                        {
                            poolType[i][j] = 1 + (4 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 6)
                    {
                        if(j < 5)
                        {
                            poolType[i][j] = 1 + (2 + Id) % 6;
                        }
                        else if(j > 11)
                        {
                            poolType[i][j] = 1 + (4 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 7)
                    {
                        if(j == 3)
                        {
                            poolType[i][j] = 1 + (2 + Id) % 6;
                        }
                        else if(j == 12)
                        {
                            poolType[i][j] = 1 + (4 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 8)
                    {
                        poolType[i][j] = 7;
                    }
                    if(i == 12)
                    {
                        if(j < 6)
                        {
                            poolType[i][j] = 1 + (1 + Id) % 6;
                        }
                        else if(j > 10)
                        {
                            poolType[i][j] = 1 + (5 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 11)
                    {
                        if(j < 5)
                        {
                            poolType[i][j] = 1 + (1 + Id) % 6;
                        }
                        else if(j > 10)
                        {
                            poolType[i][j] = 1 + (5 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 10)
                    {
                        if(j < 5)
                        {
                            poolType[i][j] = 1 + (1 + Id) % 6;
                        }
                        else if(j > 11)
                        {
                            poolType[i][j] = 1+ (5 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                    if(i == 9)
                    {
                        if(j == 3)
                        {
                            poolType[i][j] = 1 + (1 + Id) % 6;
                        }
                        else if(j == 12)
                        {
                            poolType[i][j] = 1 + (5 + Id) % 6;
                        }
                        else
                        {
                            poolType[i][j] = 7;
                        }
                    }
                }
            }
        }
    }
    private boolean isThisValidPool(int xCord, int yCord)
    {
        return !(((yCord == 13 || yCord == 3) && (xCord > 9 || xCord < 6)) ||
                ((yCord == 14 || yCord == 2) && (xCord > 9 || xCord < 7)) ||
                ((yCord == 15 || yCord == 1) && (xCord > 8 || xCord < 7)) ||
                ((yCord == 16 || yCord == 0) && (xCord != 8)) ||
                (xCord < 2) ||
                (xCord > 14) ||
                (xCord == 2 && (yCord > 5 && yCord < 11 )) ||
                (xCord == 3 && yCord == 8) ||
                (xCord == 14 && yCord > 4 && yCord < 12) ||
                (xCord == 13 && yCord > 6 && yCord < 10));
    }
    private void setColors()
    {
        for(Pool pool : pools)
        {
            int yPos = pool.yPos;
            int xPos = pool.xPos;
            switch (poolType[yPos][xPos])
            {
                case 1:
                    pool.setInsideColor(Color.MAGENTA);
                    break;
                case 2:
                    pool.setInsideColor(Color.green);
                    break;
                case 3:
                    pool.setInsideColor(Color.orange);
                    break;
                case 4:
                    pool.setInsideColor(Color.RED);
                    break;
                case 5:
                    pool.setInsideColor(Color.blue);
                    break;
                case 6:
                    pool.setInsideColor(Color.yellow);
                    break;
                case 7:
                    pool.setInsideColor(Color.gray);
                    break;

            }
        }
    }
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(Pool pool : pools)
        {
            g2d.setColor(pool.getInsideColor());
            g2d.fill(pool.ellipse2D);
        }

    }
    MouseAdapter MyMouseListener = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            for(Pool pool : pools)
            {
                if(pool.ellipse2D.contains(x,y))
                {

                }
            }
        }
    };
}
