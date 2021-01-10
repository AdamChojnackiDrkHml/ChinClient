package def;

import java.io.PrintWriter;
import java.util.Scanner;

public class CommunicationCenter
{
    Board board;
    static PrintWriter out;
    Scanner in;

    public CommunicationCenter(PrintWriter out, Board board, Scanner in)
    {
      CommunicationCenter.out = out;
      this.board = board;
      this.in = in;
    }

    public static void signalizeMove(String message)
    {
        out.println(message);
    }
    public static void signalizeEnd(PlayerId id)
    {
        out.println("END " + id.name());
    }
    public static void signalizeQuit(PlayerId id)
    {
        out.println("QUIT " + id.name());
    }
    public String interpretMessage() throws ConnectionException
    {
        String aa = in.nextLine();
        System.out.println(aa);
        if(aa.startsWith("QUIT"))
        {
            throw new ConnectionException(aa);

        }
        else if(aa.startsWith("WINNER"))
        {
            board.getGame().getMessage(aa);
            return aa;
        }
        else
        {
            board.getGame().getMessage(aa);
            board.repaint();
        }
        return " ";
    }
}
