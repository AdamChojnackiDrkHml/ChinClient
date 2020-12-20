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

    static void sendMessage(String message)
    {
        out.println(message);
    }

    void interpretMessage()
    {
        String aa = in.nextLine();
        System.out.println(aa);
        board.game.getMessage(aa);
        board.repaint();
    }
}
