package def;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import static java.lang.Thread.sleep;

/**
 * A client for a multi-player Chinese checkers toe game.
 */
public class Client implements ActionListener
{

    private JFrame frame = new JFrame("Chinese checkers");
    private JLabel messageLabel = new JLabel("Waiting for opponent's to conncet");
    private Board board;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    CommunicationCenter communicationCenter;
    private PlayerId id;
    private JButton endTurn;

    public Client(String serverAddress) throws Exception 
    {

        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        String bbb = in.nextLine();
        System.out.println(bbb);
        NumberOfPlayers numOfPlayers = NumberOfPlayers.valueOf(bbb);
        String aaa = in.nextLine();
        System.out.println(aaa);
        id = PlayerId.valueOf(aaa);
        Game game = new Game(id, numOfPlayers, new StandardGamePools());
        board = new Board(game);
        communicationCenter = new CommunicationCenter(out, board, in);
        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.NORTH);
        frame.getContentPane().add(board);
        endTurn = new JButton("End turn");
        endTurn.addActionListener(this);
        frame.getContentPane().add(endTurn, BorderLayout.SOUTH);
    }


    //Dorobiłem tą funkcję i można ją wywołać z klasy GAME, dzięki czemu możemy z GAME kontrolować kiedy wysyłamy coś do servera
    //Czeka tylko na uzupełnienie kontatku z serverem, więc na razie jest zakomentowana


    /**
     * The main thread of the client will listen for messages from the server. The
     * first message will be a "WELCOME" message in which we receive our mark. Then
     * we go into a loop listening for any of the other messages, and handling each
     * message appropriately. The "VICTORY", "DEFEAT", "TIE", and
     * "OTHER_PLAYER_LEFT" messages will ask the user whether or not to play another
     * game. If the answer is no, the loop is exited and the server is sent a "QUIT"
     * message.
     */

    public void play() throws Exception 
    {
        try {
            frame.setTitle("Chinese checkers: Player " + id.name());
            while (in.hasNextLine()) 
            {
/*                response = in.nextLine();

                else if (response.startsWith("VICTORY")) 
                {
                    JOptionPane.showMessageDialog(frame, "Winner");
                    break;
                } 
                else if (response.startsWith("DEFEAT")) 
                {
                    JOptionPane.showMessageDialog(frame, "Sorry you lost");
                    break;
                } 
                else if (response.startsWith("OTHER_PLAYER_LEFT")) 
                {
                    JOptionPane.showMessageDialog(frame, "Other player left");
                    break;
                }*/
                communicationCenter.interpretMessage();
                if(board.getGame().canIMove())
                {
                    messageLabel.setText("Your turn");
                    while(board.getGame().canIMove())
                    {
                       sleep(1);
                    }
                    messageLabel.setText("End your turn");
                    while(board.getGame().isItMyTurn())
                    {
                        sleep(1);
                    }
                    messageLabel.setText("Other player's turn");
                }
                else
                {
                    messageLabel.setText("Other player's turn");
                }

            }
            CommunicationCenter.signalizeQuit(id);
        }
        catch (ConnectionException e)
        {
            messageLabel.setText("Player has left");
            sleep(2000);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            socket.close();
            frame.dispose();
        }
    }

    public static void main(String[] args) throws Exception 
    {
        if (args.length != 1) 
        {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(40 * 18, 40 * 20);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if(source.equals(endTurn))
        {
            board.getGame().endTurn();
            board.repaint();
        }
    }




}
