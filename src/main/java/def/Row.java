package def;

import javax.swing.*;
import java.awt.*;

class Row extends JPanel
{
    private JPanel[] content = new JPanel[18];
    private boolean parity;

    public Row(int rowNum)
    {
        setBackground(Color.WHITE);
        setLayout(new SpringLayout());
        parity = rowNum % 2 == 0;
        putHalfSquare(parity);

    }
    public void initializePools()
    {
        for(JPanel pool : content)
        {
            this.add(pool);
        }
    }
    private void putHalfSquare(boolean parity)
    {
        JPanel halfSquare = new JPanel();
        halfSquare.setSize(this.getWidth() / 34, this.getHeight());
        if(parity)
        {
            content[17] = halfSquare;
        }
        else
        {
            content[0] = halfSquare;
        }
    }

    public void setContent(int num, JPanel square)
    {
        if(parity)
        {
            content[num] = square;
        }
        else
        {
            content[num + 1] = square;
        }
    }
    public JPanel getContent(int num) throws Exception
    {
        if(parity && num != 17)
            return content[num];
        else if(!parity && num != 0)
            return content[num];
        else
            throw new Exception("Invalid object");
    }
}
