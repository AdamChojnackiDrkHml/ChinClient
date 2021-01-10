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
 * A client for a multi-player Chinese checkers toe game.
 */
public class Client extends JFrame implements ActionListener
{

    private final JLabel messageLabel = new JLabel("Waiting for opponent's to connect");
    private Board board;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private CommunicationCenter communicationCenter;
    private PlayerId id;
    private JButton endTurn;

    public Client(String serverAddress) throws Exception 
    {
        super("Chinese checkers");

        initializeConnectionStuff(serverAddress);
        initializeGameStuff();
        initializeMessageLabel();
        initializeButton();
        initializeFrame();
    }

    private void initializeFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(40 * 18, 40 * 20);
        setVisible(true);
        setResizable(false);
    }
    private void initializeConnectionStuff(String serverAddress) throws IOException
    {
        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void initializeGameStuff()
    {
        NumberOfPlayers numOfPlayers = NumberOfPlayers.valueOf(in.nextLine());
        id = PlayerId.valueOf(in.nextLine());

        board = new Board(new Game(id, numOfPlayers, new StandardGamePools()));

        communicationCenter = new CommunicationCenter(out, board, in);
    }

    private void initializeMessageLabel()
    {
        messageLabel.setBackground(Color.lightGray);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        getContentPane().add(board);
    }

    private void initializeButton()
    {
        endTurn = new JButton("End turn");
        endTurn.addActionListener(this);
        getContentPane().add(endTurn, BorderLayout.SOUTH);
    }

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
        try
        {

            setTitle("Chinese checkers: Player " + id.name());

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
