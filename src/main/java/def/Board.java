package def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Board extends JPanel
{

    ArrayList<Pool> pools = new ArrayList<>();
    Game game;

    Board(Game game)
    {
        this.game = game;

        setUp();
    }
    public Color chooseColor(PlayerId id)
    {
        switch (id)
        {
            case ONE:
            {
                return Color.RED;
            }
            case TWO:
            {
                return Color.YELLOW;
            }
            case THREE:
            {
                return Color.GREEN;
            }
            case FOUR:
            {
                return Color.MAGENTA;
            }
            case FIVE:
            {
                return new Color(240, 100,0);
            }
            case SIX:
            {
                return Color.BLUE;
            }
            default:
            {
                return Color.GRAY;
            }
        }
    }


    private void choosePools()
    {
        for(int i = 0; i < 17; i++)
        {
            for(int j = 0; j < 17; j++)
            {
                if(game.gameBoard[i][j] != PlayerId.NULL)
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
                    pools.add(newPool);
                }

            }
        }
    }

    void setUp()
    {
        choosePools();
        addMouseListener(MyMouseListener);
        repaint();
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

        for(Pool pool : pools)
        {

            g2d.setColor(chooseColor(game.gameBoard[pool.yPos][pool.xPos]));
            g2d.fill(pool.ellipse2D);
            if(Arrays.equals(game.chosen, new int[]{pool.yPos, pool.xPos}))
            {
                g2d.setColor(Color.WHITE);
            }
            else
            {
                g2d.setColor(Color.black);
            }
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
            for (Pool pool : pools)
            {
                if(pool.ellipse2D.contains(x,y))
                {
                    Scanner command = new Scanner(game.decide(new int[]{pool.yPos, pool.xPos}));
                    if ("INVALID".equals(command.next()))
                    {
                        if (command.next().equals("MOVE"))
                        {
                            System.out.println("INVALID MOVE");
                        } else
                        {
                            System.out.println("INVALID POOL");
                        }
                    }
                    else
                    {
                        repaint();
                    }

                }
            }
        }
    };
}
