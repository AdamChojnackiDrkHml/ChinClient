package def;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import static java.lang.Thread.sleep;

/**
 * A client for a multi-player Chinese checkers toe game. It extends JFrame and holds the board and the game itself.
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 13254.0
 */
public class Client extends JFrame implements ActionListener
{


    private Board board;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private CommunicationCenter communicationCenter;

    private final JLabel messageLabel = new JLabel("Waiting for opponent's to connect");
    private PlayerId id;
    private JButton endTurn;

    /**
     * Client constructor, creates and initializes all components
     * @param serverAddress ip for socket connection
     * @throws Exception like a lot
     */
    public Client(String serverAddress) throws Exception 
    {
        super("Chinese checkers");

        initializeConnectionStuff(serverAddress);
        initializeGameStuff();
        initializeMessageLabel();
        initializeButton();
        initializeFrame();
    }

    /**
     * This function sets up frame properties
     */
    private void initializeFrame()
    {
        setTitle("Chinese checkers: Player " + id.name());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(40 * 18, 40 * 20);
        setVisible(true);
        setResizable(false);
    }

    /**
     * This function is responsible for setting up connection and IO handlers
     * @param serverAddress ip of the server
     * @throws IOException in case server is dead, or wrong ip address was given
     */
    private void initializeConnectionStuff(String serverAddress) throws IOException
    {
        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * This function initializes Board, Game and Communication Center. It reads values passed by a server
     * and then uses them as parameters for creating objects
     */
    private void initializeGameStuff()
    {
        NumberOfPlayers numOfPlayers = NumberOfPlayers.valueOf(in.nextLine());
        id = PlayerId.valueOf(in.nextLine());

        board = new Board(new Game(id, numOfPlayers, new StandardGameRules()));

        communicationCenter = new CommunicationCenter(out, board, in);
    }

    /**
     * This function initializes message label and adds it to the frame.
     */
    private void initializeMessageLabel()
    {
        messageLabel.setBackground(Color.lightGray);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        getContentPane().add(board);
    }

    /**
     * This function initializes end turn button and adds it to the frame.
     */
    private void initializeButton()
    {
        endTurn = new JButton("End turn");
        endTurn.addActionListener(this);
        getContentPane().add(endTurn, BorderLayout.SOUTH);
    }

    /**
     * The main thread of the client will listen for messages from the server and check for updates.
     * This function is basically responsible for calling functions when they are needed and decide what should happen
     * and who should get information. It actualises messageLabel and holds the loop until the player finishes his turn.
     */

    public void play() throws Exception 
    {
        try
        {

            while (in.hasNextLine()) 
            {
                if(board.getGame().isHaveIWon())
                {
                    JOptionPane.showMessageDialog(this, "Congratulations, you've won");
                    break;
                }


                String message = communicationCenter.interpretMessage();

                if(!message.equals(" "))
                {
                    JOptionPane.showMessageDialog(this, message + ". place");
                }


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
                }

                messageLabel.setText("Other player's turn");

            }
            messageLabel.setText("You've won. Congratulations");


            while(in.hasNextLine())
            {
                communicationCenter.interpretMessage();
                if(board.getGame().isItMyTurn())
                {
                    board.getGame().endTurn();
                }
            }
            CommunicationCenter.signalizeQuit(id);

        }
        catch (ConnectionException e)
        {
            messageLabel.setText("Player has left");
            sleep(2000);
        }
        finally 
        {
            socket.close();
            dispose();
            in.close();
            out.close();
        }
    }

    /**
     * Basic listener for end turn button click
     */
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
