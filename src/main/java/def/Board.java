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


    Board(int Id, int numOfPlayers)
    {
        setBackground(new Color(150,107,43));
        choosePools();
        setUpBoardForPlayers(numOfPlayers);
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
                        newPool = new Pool(j * 40, i * 40 + 10, 40, i, j);
                    }
                    else
                    {
                        newPool = new Pool(j * 40 + 20, i * 40 + 10,40, i, j);
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

    void setUpBoardForPlayers(int numOfPlayers)
    {
        PlayerPoolsInterface gamePoolsRules = new StandardGamePools();
        switch (numOfPlayers)
        {
            case 2:
                pools = gamePoolsRules.setBoardForTwoPlayers(pools);
                break;
            case 3:
                pools = gamePoolsRules.setBoardForThreePlayers(pools);
                break;
            case 4:
                pools = gamePoolsRules.setBoardForFourPlayers(pools);
                break;
            case 6:
                pools = gamePoolsRules.setBoardForSixPlayers(pools);
                break;
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
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(Pool pool : pools)
        {
            g2d.setColor(pool.getInsideColor());
            g2d.fill(pool.ellipse2D);
            g2d.setColor(pool.getBorderColor());
            g2d.draw(pool.ellipse2D);
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
