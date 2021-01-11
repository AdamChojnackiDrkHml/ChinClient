package def;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is responsible for scanning input and sending output to server
 * @author Adam Chojnacki i Ela Wiśniewska
 * @version 4.0
 */
public class CommunicationCenter
{
    Board board;
    static PrintWriter out;
    Scanner in;

    /**
     * Basic constructor, nothing special
     * @param out PrintWriter to socket
     * @param board Just a gameboard
     * @param in Scanner from socket
     */
    public CommunicationCenter(PrintWriter out, Board board, Scanner in)
    {
      CommunicationCenter.out = out;
      this.board = board;
      this.in = in;
    }

    /**
     * This function send move instructions to server
     * @param message move instruction
     */
    public static void signalizeMove(String message)
    {
        out.println(message);
    }

    /**
     * This function send information about ending move to the server
     * @param id id of a player sending the message
     */
    public static void signalizeEnd(PlayerId id)
    {
        out.println("END " + id.name());
    }

    /**
     * This function should send information about player quiting the game to the server
     * @param id id of a quiting player
     */
    public static void signalizeQuit(PlayerId id)
    {
        out.println("QUIT " + id.name());
    }

    /**
     * This function is a little messed up, but it is responsible for taking input from socket
     * and then sending it to a proper place.
     * @return this function should return information about winner if met
     * @throws ConnectionException if something goes wrong
     */
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
