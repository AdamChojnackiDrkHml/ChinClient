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
    void interpretMessage() throws ConnectionException
    {
        String aa = in.nextLine();
        System.out.println(aa);
        if(!aa.startsWith("QUIT"))
        {
            board.getGame().getMessage(aa);
            board.repaint();
        }
        else
        {
            throw new ConnectionException(aa);
        }
    }
}
