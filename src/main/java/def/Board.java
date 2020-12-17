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
    int[] currentPoolIndex = new int[2];
    boolean isChosen;
    PlayerPoolsInterface gamePoolsRules = new StandardGamePools();
    Color playerColor;


    Board(int Id, int numOfPlayers)
    {
       // setBackground(new Color(147, 197, 232, 255));
        choosePools();
        chooseColor(Id);
        setUpBoardForPlayers(numOfPlayers);
        addMouseListener(MyMouseListener);
        repaint();
    }

    private void chooseColor(int id)
    {
        switch(id)
        {
            case(1):
            {
                playerColor = Color.RED;
                break;
            }
            case(2):
            {
                playerColor = Color.YELLOW;
                break;
            }
            case(3):
            {
                playerColor = Color.GREEN;
                break;
            }
            case(4):
            {
                playerColor = Color.MAGENTA;
                break;
            }
            case(5):
            {
                playerColor = new Color(240, 100, 0);
                break;
            }
            case(6):
            {
                playerColor = Color.BLUE;
                break;
            }
        }
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
        Color color1 = new Color(52,143,80);
        Color color2 = new Color(86,180,211);
        GradientPaint gp = new GradientPaint(0,0, color1,180, this.getHeight(), color2 );
        g2d.setPaint(gp);
        g2d.fillRect(0,0,this.getWidth(), this.getHeight());

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
                            if (pool.ellipse2D.contains(x, y) && pool.getInsideColor().equals(playerColor))
                            {
                                pool.setBorderColor(Color.WHITE);
                                isChosen = true;
                                currentPoolIndex[0] = pool.yPos;
                                currentPoolIndex[1] = pool.xPos;
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
                                else if(gamePoolsRules.isMoveValid(currentPool.yPos, currentPool, pool ) && pool.getInsideColor().equals(Color.GRAY))
                                {
                                    pool.setInsideColor(currentPool.getInsideColor());
                                    pools2[currentPoolIndex[0]][currentPoolIndex[1]].setInsideColor(Color.GRAY);
                                    pools2[currentPoolIndex[0]][currentPoolIndex[1]].setBorderColor(Color.BLACK);
                                    isChosen = false;
                                    repaint();
                                }
                            }
                        }
                    }
                }
            }
        }
    };
}
