package def;

import java.util.Arrays;
import java.util.Scanner;

public class Game
{
   PlayerId[][] gameBoard = new PlayerId[17][17];
   int[] chosen = new int[2];
   boolean isChosen = false;
   PlayerPoolsInterface gamePoolsRules;
   PlayerId playerId;



   Game(PlayerId id, NumberOfPlayers numOfPlayers, PlayerPoolsInterface gamePoolsRules)
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
        try
        {
            PlayerId playerMovedId = PlayerId.valueOf(scanner.next());
            int xBeg = Integer.parseInt(scanner.next());
            int yBeg = Integer.parseInt(scanner.next());
            int xDest = Integer.parseInt(scanner.next());
            int yDest = Integer.parseInt(scanner.next());

            gameBoard[yBeg][xBeg] = PlayerId.ZERO;
            gameBoard[xDest][yDest] = playerMovedId;

        }
        catch (NumberFormatException ignored)
        {

        }
    }
    public String decide(int[] pos)
    {
        if(!isChosen)
        {
            if (gameBoard[pos[0]][pos[1]].equals(playerId))
            {
                chosen = pos;
                isChosen = true;
                return "CHOSEN";
            }
            else
            {
                return "INVALID POOL";
            }
        }
        else
        {
            if(gameBoard[pos[0]][pos[1]].equals(playerId))
            {
                if(Arrays.equals(chosen, pos))
                {
                    isChosen = false;
                    chosen = new int[]{0, 0};
                    return "UNCHOOSEN";
                }
                else
                {
                    chosen = pos;
                    isChosen = true;
                    return "CHOSEN";
                }
            }
            else if(gameBoard[pos[0]][pos[1]].equals(PlayerId.ZERO) && gamePoolsRules.isMoveValid(chosen[0], chosen, pos))
            {
                gameBoard[pos[0]][pos[1]] = playerId;
                gameBoard[chosen[0]][chosen[1]] = PlayerId.ZERO;
                chosen = new int[]{0,0};
                isChosen = false;
                Client.notifyServer();
                return "MOVED";
            }
            else
            {
                return "INVALID MOVE";
            }
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        PlayerId[][] board = game.gameBoard;
        for(PlayerId[] row : board)
        {
            for(PlayerId pool : row)
            {
                String i = " ";
                switch (pool)
                {
                    case ZERO:
                    {
                        i+="0 ";
                        break;
                    }
                    case ONE:
                    {
                        i+="1 ";
                        break;
                    }
                    case TWO:
                    {
                        i+="2 ";
                        break;
                    }
                    case THREE:
                    {
                        i+="3 ";
                        break;
                    }
                    case FOUR:
                    {
                        i+="4 ";
                        break;
                    }
                    case FIVE:
                    {
                        i+="5 ";
                        break;
                    }
                    case SIX:
                    {
                        i+="6 ";
                        break;
                    }
                    default:
                    {

                        i+=" ";
                        break;
                    }
                }
                System.out.println(i + "\n");
            }
        }
    }
}
