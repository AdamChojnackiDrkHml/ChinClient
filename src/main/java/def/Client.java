package def;

import java.awt.*;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

/**
 * A client for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book. For this project I created a
 * new application-level protocol called TTTP (for Tic Tac Toe Protocol), which
 * is entirely plain text. The messages of TTTP are:
 *
 * Client -> Server MOVE <n> QUIT
 *
 * Server -> Client WELCOME <char> VALID_MOVE OTHER_PLAYER_MOVED <n>
 * OTHER_PLAYER_LEFT VICTORY DEFEAT TIE MESSAGE <text>
 */
public class Client {

    private JFrame frame = new JFrame("Tic Tac Toe");
    private JLabel messageLabel = new JLabel("...");


    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Client(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 58900);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(in.next());

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        frame.getContentPane().add(new Board(5, 6));
     //   frame.pack();
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
    public void play() throws Exception {
  /*      try {
            String response = in.nextLine();
            char mark = response.charAt(8);
            char opponentMark = mark == 'X' ? 'O' : 'X';
            frame.setTitle("Tic Tac Toe: Player " + mark);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    currentSquare.setText(mark);
                    currentSquare.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
               //     board[loc][5].setText(opponentMark);
                 //   board[loc][5].repaint();
                    messageLabel.setText("Opponent moved, your turn");
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    JOptionPane.showMessageDialog(frame, "Winner Winner");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    JOptionPane.showMessageDialog(frame, "Sorry you lost");
                    break;
                } else if (response.startsWith("TIE")) {
                    JOptionPane.showMessageDialog(frame, "Tie");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    JOptionPane.showMessageDialog(frame, "Other player left");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            frame.dispose();
        } */
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(40 * 18, 40 * 19);
      //  client.frame.revalidate();
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }
}
