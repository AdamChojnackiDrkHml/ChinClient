package def;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The class that holds whole game, game current state and logic.
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 20.0
 */
public class Game
{
    private static final int NumberOfCoordinates = 2;
    private static final int xCoordinatePlaceInArray = 1;
    private static final int yCoordinatePlaceInArray = 0;
    private static final int[] DefaultCoordinateArray = {0,0};


    private PlayerId[][] gameBoard = new PlayerId[GameRulesInterface.BoardSize][GameRulesInterface.BoardSize];
    private final GameRulesInterface gamePoolsRules;
    private final PlayerId playerId;


    private int[] chosen = new int[NumberOfCoordinates];
    private int[] prevJumpPos = new int[NumberOfCoordinates];

    //Set of flags used to control logic
    private boolean isChosen = false;
    private boolean isItMyTurn = false;
    private boolean canIMove = false;
    private boolean isJumping = false;
    private boolean haveIWon = false;


    /**
     * Game constructor
     * @param id this Player id
     * @param numOfPlayers number of Players
     * @param gamePoolsRules rules chosen for this game
     */
   public Game(PlayerId id, NumberOfPlayers numOfPlayers, GameRulesInterface gamePoolsRules)
   {
       this.gamePoolsRules = gamePoolsRules;
       this.playerId = id;
       choosePools(numOfPlayers);
   }

    /**
     * This function is pretty straightforward, it decides whether the pool is contained in board or not
     * There is a lot of coordinates and magic numbers, naming all of them would completely mess up
     * clarity
     * @param xCord x coordinate of pool
     * @param yCord y coordinate of pool
     * @return true if it is contained, false otherwise
     */
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

    /**
     * This function initializes an actual gameBoard, inserting Zero player token if pool belongs to board and null otherwise.
     * It then calls for rules function to fill the board with player tokens.
     * @param numOfPlayers number of players
     */
    private void choosePools(NumberOfPlayers numOfPlayers)
    {
        for(int i = 0; i < GameRulesInterface.BoardSize; i++)
        {
            for(int j = 0; j < GameRulesInterface.BoardSize; j++)
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

    /**
     * This function is responsible for interpreting the message and taking proper actions based on this.
     * @param message message passed by CommunicationCenter
     */
    public void getMessage(String message)
    {
        Scanner scanner = new Scanner(message);
        String order = scanner.next();
        switch (order)
        {
            case "MOVE":
                try
                {
                    PlayerId playerMovedId = PlayerId.valueOf(scanner.next());
                    int xBeg = Integer.parseInt(scanner.next());
                    int yBeg = Integer.parseInt(scanner.next());
                    int xDest = Integer.parseInt(scanner.next());
                    int yDest = Integer.parseInt(scanner.next());

                    gameBoard[yBeg][xBeg] = PlayerId.ZERO;
                    gameBoard[yDest][xDest] = playerMovedId;
                } catch (NumberFormatException ignored)
                {

                }
                break;
            case "YOUR_MOVE":
                isItMyTurn = true;
                canIMove = true;
                break;
            case "WINNER":
                if (PlayerId.valueOf(scanner.next()).equals(playerId))
                {
                    haveIWon = true;
                    endTurn();
                }

                break;
        }
        scanner.close();
    }

    /**
     * This function holds the deciding process for the game. It calls for proper rules functions basing on flags state
     * and call for sending message to server if needed. It also actualises flags.
     * @param pos array containing position on gameboard passed by Board
     * @throws IncorrectFieldException if wrong pool is clicked
     */
    public void decide(int[] pos) throws IncorrectFieldException
    {
        if(haveIWon)
        {
            endTurn();
            return;
        }
        if(canIMove)
        {
            if (!isChosen && !isJumping)
            {
                if (gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]].equals(playerId))
                {
                    chosen = pos;
                    isChosen = true;
                }
                else
                {
                    throw new IncorrectFieldException("INVALID POOL");
                }
            }
            else
            {
                if (gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]].equals(playerId) && !isJumping)
                {
                    if (Arrays.equals(chosen, pos))
                    {
                        isChosen = false;
                        chosen = DefaultCoordinateArray;
                    }
                    else
                    {
                        chosen = pos;
                    }
                }
                else if (gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]].equals(PlayerId.ZERO)
                        && gamePoolsRules.isMoveValid(chosen, pos, playerId) && !isJumping)
                {
                    CommunicationCenter.signalizeMove("MOVE");
                    CommunicationCenter.signalizeMove(playerId.name() + " " + chosen[1] + " " + chosen[0] + " " + pos[1] + " " + pos[0]);
                    gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]] = playerId;
                    gameBoard[chosen[0]][chosen[1]] = PlayerId.ZERO;
                    chosen = DefaultCoordinateArray;
                    isChosen = false;
                    canIMove = false;
                }
                else if(gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]].equals(PlayerId.ZERO)
                        && gamePoolsRules.isJumpValid(chosen, pos, playerId)
                        && gamePoolsRules.jumpCondition(gameBoard, chosen, pos, prevJumpPos))
                {
                    CommunicationCenter.signalizeMove("MOVE JUMP");
                    CommunicationCenter.signalizeMove(playerId.name() + " " + chosen[1] + " " + chosen[0] + " " + pos[1] + " " + pos[0]);
                    gameBoard[pos[yCoordinatePlaceInArray]][pos[xCoordinatePlaceInArray]] = playerId;
                    gameBoard[chosen[yCoordinatePlaceInArray]][chosen[xCoordinatePlaceInArray]] = PlayerId.ZERO;
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


    /**
     * This function ends turn, and sets all flags and handlers to "Not my turn" state
     * It also calls for signalizing turn end function
     */
    public void endTurn()
    {

        if(isItMyTurn)
        {
            chosen = DefaultCoordinateArray;
            prevJumpPos = DefaultCoordinateArray;
            isChosen = false;
            isJumping = false;
            canIMove = false;
            isItMyTurn = false;
            CommunicationCenter.signalizeEnd(playerId);
        }
    }

    public PlayerId[][] getGameBoard()
    {
        return gameBoard;
    }


    public boolean isItMyTurn()
    {
        return isItMyTurn;
    }

    public boolean canIMove()
    {
        return canIMove;
    }

    public boolean isHaveIWon()
    {
        return haveIWon;
    }

    public int[] getChosen()
    {
        return chosen;
    }
}
