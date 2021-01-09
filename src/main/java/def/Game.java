package def;

import java.util.Arrays;
import java.util.Scanner;

public class Game
{
   private PlayerId[][] gameBoard = new PlayerId[17][17];
   int[] chosen = new int[2];
   int[] prevJumpPos = new int[2];
   boolean isChosen = false;
   PlayerPoolsInterface gamePoolsRules;
   PlayerId playerId;
   public boolean isItMyTurn = false;
   private boolean canIMove = false;
   private boolean isJumping = false;

   public Game(PlayerId id, NumberOfPlayers numOfPlayers, PlayerPoolsInterface gamePoolsRules)
   {
       this.gamePoolsRules = gamePoolsRules;
       this.playerId = id;
       choosePools(numOfPlayers);
   }
    private boolean isThisValidPool(int xCord, int yCord)
    {
        return !(((yCord == 13 || yCord == 3) && (xCord > 9 || xCord < 6)) ||
                ((yCord == 14 || yCord == 2) && (xCord > 9 || xCord < 7)) ||
                ((yCord == 15 || yCord == 1) && (xCord > 8 || xCord < 7)) ||
                ((yCord == 16 || yCord == 0) && (xCord != 8)) ||
                (xCord < 2) ||
                (xCord > 14) ||
                (xCord == 2 && (yCord > 5 && yCord < 11 )) ||
                (xCord == 3 && yCord == 8) ||
                (xCord == 14 && yCord > 4 && yCord < 12) ||
                (xCord == 13 && yCord > 6 && yCord < 10));
    }

    private void choosePools(NumberOfPlayers numOfPlayers)
    {
        for(int i = 0; i < 17; i++)
        {
            for(int j = 0; j < 17; j++)
            {
                if(isThisValidPool(j,i))
                {
                    gameBoard[i][j] = PlayerId.ZERO;
                }
                else
                {
                    gameBoard[i][j] = PlayerId.NULL;
                }
            }
        }
        gameBoard = gamePoolsRules.setUpBoardForPlayers(numOfPlayers, gameBoard);
    }

    public void getMessage(String message)
    {
        Scanner scanner = new Scanner(message);
        String order = scanner.next();
        if (order.equals("MOVE"))
        {
            try
            {
                PlayerId playerMovedId = PlayerId.valueOf(scanner.next());
                int xBeg = Integer.parseInt(scanner.next());
                int yBeg = Integer.parseInt(scanner.next());
                int xDest = Integer.parseInt(scanner.next());
                int yDest = Integer.parseInt(scanner.next());

                gameBoard[yBeg][xBeg] = PlayerId.ZERO;
                gameBoard[yDest][xDest] = playerMovedId;
            } 
            catch (NumberFormatException ignored)
            {

            }
        }
        else if (order.equals("YOUR_MOVE"))
        {
           	isItMyTurn = true;
           	canIMove = true;
        }
        else if (order.equals("WINNER"))
        {
        	String winner = scanner.nextLine();
        	//winner to wiadomosc o tresci "IS PLAYER X"
        	//tu trzeba jakos zrobic tak, ze w okienku wyswietli sie komunikat "WINNER " + winner
        }
        scanner.close();
    }
    public void decide(int[] pos) throws IncorrectFieldException
    {
        if(canIMove)
        {
            if (!isChosen && !isJumping)
            {
                if (gameBoard[pos[0]][pos[1]].equals(playerId))
                {
                    chosen = pos;
                    isChosen = true;
                } else
                {
                    throw new IncorrectFieldException("INVALID POOL");
                }
            } else
            {
                if (gameBoard[pos[0]][pos[1]].equals(playerId) && !isJumping)
                {
                    if (Arrays.equals(chosen, pos))
                    {
                        isChosen = false;
                        chosen = new int[]{0, 0};
                    } else
                    {
                        chosen = pos;
                    }
                }
                else if (gameBoard[pos[0]][pos[1]].equals(PlayerId.ZERO) && gamePoolsRules.isMoveValid(chosen[0], chosen, pos, playerId) && !isJumping)
                {
                    CommunicationCenter.signalizeMove("MOVE");
                    CommunicationCenter.signalizeMove(playerId.name() + " " + chosen[1] + " " + chosen[0] + " " + pos[1] + " " + pos[0]);
                    gameBoard[pos[0]][pos[1]] = playerId;
                    gameBoard[chosen[0]][chosen[1]] = PlayerId.ZERO;
                    chosen = new int[]{0, 0};
                    isChosen = false;
                    canIMove = false;
                }
                //Tutaj jest obsługa skakania itd, wysyłany jest najpierw sygnał MOVE JUMP, a na koniec ustawiana flaga od skakania, żeby można było tylko skakać
                else if(gameBoard[pos[0]][pos[1]].equals(PlayerId.ZERO) && gamePoolsRules.isJumpValid(chosen, pos, playerId) && gamePoolsRules.jumpCondition(gameBoard, chosen, pos, prevJumpPos))
                {
                    CommunicationCenter.signalizeMove("MOVE JUMP");
                    CommunicationCenter.signalizeMove(playerId.name() + " " + chosen[1] + " " + chosen[0] + " " + pos[1] + " " + pos[0]);
                    gameBoard[pos[0]][pos[1]] = playerId;
                    gameBoard[chosen[0]][chosen[1]] = PlayerId.ZERO;
                    prevJumpPos = chosen;
                    chosen = pos;
                    isJumping = true;
                }
                else
                {
                    throw new IncorrectFieldException("INVALID MOVE");
                }

            }
        }
    }

    public PlayerId[][] getGameBoard()
    {
        return gameBoard;
    }
    public void endTurn()
    {

        if(isItMyTurn)
        {
            System.out.println("ALERT");
            chosen = new int[]{0, 0};
            prevJumpPos = new int[]{0,0};
            isChosen = false;
            isJumping = false;
            isItMyTurn = false;
            CommunicationCenter.signalizeEnd(playerId);
        }
    }

    public boolean isItMyTurn()
    {
        return isItMyTurn;
    }

    public boolean canIMove()
    {
        return canIMove;
    }
}
