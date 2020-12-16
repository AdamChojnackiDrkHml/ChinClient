package def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Board extends JPanel
{

    Pool[][] pools2 = new Pool[17][17];
    Pool currentPool;
    boolean isChosen;


    Board(int Id, int numOfPlayers)
    {
        setBackground(new Color(150,107,43));
        choosePools();
        setUpBoardForPlayers(numOfPlayers);
        addMouseListener(MyMouseListener);
        repaint();
    }

    private void choosePools()
    {
        for(int i = 0; i < 17; i++)
        {
            for(int j = 0; j < 17; j++)
            {
                if(isThisValidPool(j,i))
                {
                    Pool newPool;
                    if(i % 2 == 0)
                    {
                        newPool = new Pool(j * 40, i * 40 + 10, 40, i, j);
                    }
                    else
                    {
                        newPool = new Pool(j * 40 + 20, i * 40 + 10,40, i, j);
                    }
                    pools2[i][j] = newPool;
                }
                else
                {
                    pools2[i][j] = null;
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
                pools2 = gamePoolsRules.setBoardForTwoPlayers(pools2);
                break;
            case 3:
                pools2 = gamePoolsRules.setBoardForThreePlayers(pools2);
                break;
            case 4:
                pools2 = gamePoolsRules.setBoardForFourPlayers(pools2);
                break;
            case 6:
                pools2 = gamePoolsRules.setBoardForSixPlayers(pools2);
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
        for(Pool[] row : pools2)
        {
            for(Pool pool : row)
            {
                if (pool != null)
                {
                    g2d.setColor(pool.getInsideColor());
                    g2d.fill(pool.ellipse2D);
                    g2d.setColor(pool.getBorderColor());
                    g2d.draw(pool.ellipse2D);
                }
            }
        }

    }

    MouseAdapter MyMouseListener = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            if(!isChosen)
            {
search:         for(Pool[] row : pools2)
                {
                    for (Pool pool : row)
                    {
                        if(pool != null)
                        {
                            if (pool.ellipse2D.contains(x, y))
                            {

                                pool.setBorderColor(Color.WHITE);
                                isChosen = true;
                                currentPool = pool;
                                repaint();
                                break search;

                            }
                        }
                    }
                }
            }
            else
            {
search:         for(Pool[] row : pools2)
                {
                    for (Pool pool : row)
                    {
                        if(pool != null)
                        {
                            if (pool.ellipse2D.contains(x, y))
                            {
                                if(pool.xPos == currentPool.xPos && pool.yPos == currentPool.yPos)
                                {
                                    pool.setBorderColor(Color.BLACK);
                                    isChosen = false;
                                    currentPool = null;
                                    repaint();
                                    break search;
                                }
                            }
                        }
                    }
                }
            }
        }
    };
}
