package def;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommunicationCenter
{
    Board board;
    static PrintWriter out;
    Scanner in;

    CommunicationCenter(PrintWriter out, Board board, Scanner in)
    {
      CommunicationCenter.out = out;
      this.board = board;
      this.in = in;
    }

    static void signalizeMove(String message)
    {
        out.println(message);
    }
    static void signalizeEnd(PlayerId id)
    {
        out.println("END " + id.name());
    }
    static void signalizeQuit(PlayerId id)
    {
        out.println("QUIT " + id.name());
    }
    String interpretMessage() throws ConnectionException
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
