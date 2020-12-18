package def;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommunicationCenter
{
    Board board;
    PrintWriter out;
    Scanner in;

    CommunicationCenter(PrintWriter out, Board board, Scanner in)
    {
      this.out = out;
      this.board = board;
      this.in = in;
    }

    void sendMessage(String message)
    {
        out.println(message);
    }

    void interpretMessage()
    {
        board.game.getMessage(in.nextLine());
        board.repaint();
    }
}
