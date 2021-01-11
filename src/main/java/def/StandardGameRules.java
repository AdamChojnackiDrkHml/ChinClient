package def;


import java.util.Arrays;

/**
 *  This class implements rules interface and is responsible for handling game rules and questions.
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 10.0
 */
public class StandardGameRules implements GameRulesInterface
{
    private static final int xCoordinatePlaceInArray = 1;
    private static final int yCoordinatePlaceInArray = 0;


    int[][] UpperPools = {{0,8}, {1,7}, {1,8}, {2,7}, {2,8}, {2,9}, {3,6}, {3,7}, {3,8}, {3,9}};
    int[][] UpperLeftPools = {{4,2}, {4,3}, {4,4}, {4,5}, {5,2}, {5,3}, {5,4}, {6,3}, {6,4}, {7,3}};
    int[][] UpperRightPools = {{4,11}, {4,12}, {4,13}, {4,14}, {5,11}, {5,12}, {5,13}, {6,12}, {6,13}, {7,12}};
    int[][] BottomLeftPools = {{9,3}, {10,3}, {10,4}, {11,2}, {11,3}, {11,4}, {12,2}, {12,3}, {12,4}, {12,5}};
    int[][] BottomRightPools = {{12,11}, {12,12}, {12,13}, {12,14}, {11,11}, {11,12}, {11,13}, {10,12}, {10,13}, {9,12}};
    int[][] BottomPools = {{16,8}, {15,7}, {15,8}, {14,7}, {14,8}, {14,9}, {13,6}, {13,7}, {13,8}, {13,9}};
    int[][][] pools = {BottomPools, BottomLeftPools, UpperLeftPools, UpperPools, UpperRightPools, BottomRightPools};

    int[][] possibleParityMoves = {{1, 0},{1,-1},{-1, 0},{-1, -1},{0, 1},{0, -1}};
    int[][] possibleOddMoves = {{1,1},{1,0},{-1,1},{-1,0}, {0,1},{0,-1}};

    int[][] possibleJumps = { {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {0,2}, {0,-2}};


    /**
     * This function checks if player is currently in enemy base and if so is he trying to exit it, which is forbidden.
     * @param originalPosition original Player position
     * @param desirePosition position that Player wants to move on
     * @param id Players' id
     * @return true if player is trying to make restricted move, false otherwise
     */
    @Override
    public boolean checkIfInEnemyBase(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        int enemyId = (PlayerId.getInt(id) + 2) % 6;
        boolean isOriginInBase = false;
        boolean isDestInBase = false;

        for(int i = 0; i < GameRulesInterface.NumberOfPoolsInEnemyBase; i++)
        {
            if(pools[enemyId][i][yCoordinatePlaceInArray] == originalPosition[yCoordinatePlaceInArray]
                    && pools[enemyId][i][xCoordinatePlaceInArray] == originalPosition[yCoordinatePlaceInArray])
            {
                isOriginInBase = true;
            }
            if(pools[enemyId][i][yCoordinatePlaceInArray] == desirePosition[yCoordinatePlaceInArray]
                    && pools[enemyId][i][xCoordinatePlaceInArray] == desirePosition[xCoordinatePlaceInArray])
            {
                isDestInBase = true;
            }
        }
        return (isOriginInBase && !isDestInBase);
    }


    /**
     * This function checks if player is making accepted move
     * @param originalPosition original Player position
     * @param desirePosition position that Player wants to move on
     * @param id moving players' id
     * @return true if move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        if(checkIfInEnemyBase(originalPosition, desirePosition, id))
        {
            return false;
        }
        int moveX = desirePosition[xCoordinatePlaceInArray] - originalPosition[xCoordinatePlaceInArray];
        int moveY = desirePosition[yCoordinatePlaceInArray] - originalPosition[yCoordinatePlaceInArray];
        if(originalPosition[yCoordinatePlaceInArray] % 2 == 0)
        {
            for(int[] move : possibleParityMoves)
            {
                if(moveX == move[xCoordinatePlaceInArray] && moveY == move[xCoordinatePlaceInArray])
                {
                    return true;
                }
            }
        }
        else
        {
            for(int[] move : possibleOddMoves)
            {
                if(moveX == move[xCoordinatePlaceInArray] && moveY == move[yCoordinatePlaceInArray])
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This function decides if the jump that player tries don't violate the rules of jumping basic convention
     * (jump is set of two pools move, in straight direction)
     * @param originalPosition original Player position
     * @param desirePosition position that Player wants to move on
     * @param id Players' id
     * @return true if jump is valid, false otherwise
     */
    @Override
    public boolean isJumpValid(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        if(checkIfInEnemyBase(originalPosition, desirePosition, id))
        {
            return false;
        }
        int moveX = desirePosition[xCoordinatePlaceInArray] - originalPosition[xCoordinatePlaceInArray];
        int moveY = desirePosition[yCoordinatePlaceInArray] - originalPosition[yCoordinatePlaceInArray];
        int[] direction = possibleJumps[getDirection(moveX, moveY)];

        for(int i = 0; i < 7; i++)
        {

            if(direction[yCoordinatePlaceInArray] * i == moveY && direction[xCoordinatePlaceInArray] * i == moveX)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This function checks if player doesn't try to jump back and if player jumps over exactly one pawn that lies exactly
     * in the middle of a jump.
     * @param board game board is passed to check for pawns
     * @param originalPosition original Player position
     * @param desirePosition position that Player wants to move on
     * @param previousJumpPos start position of last jump to prevent jumping back
     * @return true if jump is valid, false otherwise
     */
    @Override
    public boolean jumpCondition(PlayerId[][] board, int[] originalPosition, int[] desirePosition, int[] previousJumpPos)
    {
        int moveX = desirePosition[xCoordinatePlaceInArray] - originalPosition[xCoordinatePlaceInArray];
        int moveY = desirePosition[yCoordinatePlaceInArray] - originalPosition[yCoordinatePlaceInArray];
        int direction = getDirection(moveX, moveY);
        if (Arrays.equals(previousJumpPos, desirePosition))
            return false;
        if (originalPosition[yCoordinatePlaceInArray] % 2 != desirePosition[yCoordinatePlaceInArray] % 2)
            return false;
        int[] parityDirection = possibleParityMoves[direction];
        int[] oddDirection = possibleOddMoves[direction];
        int counter = 0;
        int distance = 0;
        int xCord = originalPosition[xCoordinatePlaceInArray];
        int yCord = originalPosition[yCoordinatePlaceInArray];
        PlayerId check;
        for (int i = 0; i < 14; i++)
        {
            if (yCord % 2 == 0)
            {
                yCord += parityDirection[yCoordinatePlaceInArray];
                xCord += parityDirection[xCoordinatePlaceInArray];
            } else
            {
                yCord += oddDirection[yCoordinatePlaceInArray];
                xCord += oddDirection[xCoordinatePlaceInArray];
            }
            check = board[yCord][xCord];
            if(check.equals(PlayerId.NULL))
            {
                return false;
            }
            if (!check.equals(PlayerId.ZERO))
            {
                counter++;
            }
            distance++;
            if (desirePosition[yCoordinatePlaceInArray] == yCord
                    && desirePosition[xCoordinatePlaceInArray] == xCord)
            {
                break;
            }
        }
        if(counter != 1)
        {
            return false;
        }
        xCord = originalPosition[xCoordinatePlaceInArray];
        yCord = originalPosition[yCoordinatePlaceInArray];
        for (int i = 0; i < (distance / 2); i++)
        {
            if (yCord % 2 == 0)
            {
                yCord += parityDirection[yCoordinatePlaceInArray];
                xCord += parityDirection[xCoordinatePlaceInArray];
            } else
            {
                yCord += oddDirection[yCoordinatePlaceInArray];
                xCord += oddDirection[xCoordinatePlaceInArray];
            }
        }
        check = board[yCord][xCord];
        return (!check.equals(PlayerId.ZERO));

    }

    /**
     * This function determines one of six directions of player move
     * @param moveX x axis move
     * @param moveY y axis move
     * @return number from 1 to 6, depending on arguments passed, counting from 1 which is bottom right move, clockwise
     */
    @Override
    public int getDirection(int moveX, int moveY)
    {
        if(moveX > 0)
        {
            if(moveY > 0)
            {
                return 0;

            }
            else if(moveY < 0)
            {
                return 2;
            }
            else
            {
                return 4;
            }
        }
        else if(moveX < 0)
        {
            if(moveY > 0)
            {
                return 1;

            }
            else if(moveY < 0)
            {
                return 3;
            }
            else
            {
                return 5;
            }
        }
        return 0;
    }

    /**
     * This function sets player pawns on a board for player four
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setUpperPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.FOUR;
        }
        return pools;
    }

    /**
     * This function sets player pawns on a board for player three
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setUpperLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperLeftPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.THREE;
        }
        return pools;
    }

    /**
     * This function sets player pawns on a board for player five
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setUpperRightPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperRightPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.FIVE;
        }
        return pools;
    }

    /**
     * This function sets player pawns on a board for player two
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBottomLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomLeftPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.TWO;
        }
        return pools;
    }

    /**
     * This function sets player pawns on a board for player six
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBottomRightPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomRightPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.SIX;
        }
        return pools;
    }

    /**
     * This function sets player pawns on a board for player one
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBottomPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomPools)
        {
            pools[cords[yCoordinatePlaceInArray]][cords[xCoordinatePlaceInArray]] = PlayerId.ONE;
        }
        return pools;
    }

    /**
     * This function sets player pawns for two players game
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBoardForTwoPlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperPools(pools));
    }

    /**
     * This function sets player pawns on a board for three players game
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBoardForThreePlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperLeftPools(setUpperRightPools(pools)));

    }

    /**
     * This function sets player pawns on a board for four players game
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBoardForFourPlayers(PlayerId[][] pools)
    {
        return setUpperRightPools(setBottomLeftPools(setBoardForTwoPlayers(pools)));

    }

    /**
     * This function sets player pawns on a board for six players game
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setBoardForSixPlayers(PlayerId[][] pools)
    {
        return setBoardForFourPlayers(setUpperLeftPools(setBottomRightPools(pools)));
    }

    /**
     * This function sets player pawns on a board depending on passed number of players
     * @param numOfPlayers number of players in a game
     * @param pools game board
     * @return game board set with pawns
     */
    @Override
    public PlayerId[][] setUpBoardForPlayers(NumberOfPlayers numOfPlayers, PlayerId[][] pools)
    {
        switch (numOfPlayers)
        {
            case TWO:
            {
                return setBoardForTwoPlayers(pools);
            }
            case THREE:
            {
                return setBoardForThreePlayers(pools);
            }
            case FOUR:
            {
                return setBoardForFourPlayers(pools);
            }
            case SIX:
            {
                return setBoardForSixPlayers(pools);
            }
            default:
            {
                return setBottomPools(pools);
            }
        }
    }

}
