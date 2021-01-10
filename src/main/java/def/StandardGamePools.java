package def;


import java.util.Arrays;

public class StandardGamePools implements PlayerPoolsInterface
{
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


    @Override
    public boolean checkIfInEnemyBase(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        int enemyId = (PlayerId.getInt(id) + 2) % 6;
        boolean isOriginInBase = false;
        boolean isDestInBase = false;

        for(int i = 0; i < 10; i++)
        {
            if(pools[enemyId][i][0] == originalPosition[0] && pools[enemyId][i][1] == originalPosition[1])
            {
                isOriginInBase = true;
            }
            if(pools[enemyId][i][0] == desirePosition[0] && pools[enemyId][i][1] == desirePosition[1])
            {
                isDestInBase = true;
            }
        }
        return (isOriginInBase && !isDestInBase);
    }
    @Override
    public boolean isMoveValid(int rowNum, int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        if(checkIfInEnemyBase(originalPosition, desirePosition, id))
        {
            return false;
        }
        int moveX = desirePosition[1] - originalPosition[1];
        int moveY = desirePosition[0] - originalPosition[0];
        if(rowNum % 2 == 0)
        {
            for(int[] move : possibleParityMoves)
            {
                if(moveX == move[1] && moveY == move[0])
                {
                    return true;
                }
            }
        }
        else
        {
            for(int[] move : possibleOddMoves)
            {
                if(moveX == move[1] && moveY == move[0])
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isJumpValid(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        if(checkIfInEnemyBase(originalPosition, desirePosition, id))
        {
            return false;
        }
        int moveX = desirePosition[1] - originalPosition[1];
        int moveY = desirePosition[0] - originalPosition[0];
        int[] direction = possibleJumps[getDirection(moveX, moveY)];

        for(int i = 0; i < 14; i++)
        {

            if(direction[0] * i == moveY && direction[1] * i == moveX)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean jumpCondition(PlayerId[][] board, int[] originalPosition, int[] desirePosition, int[] previousJumpPos)
    {
        int direction = getDirection(desirePosition[1] - originalPosition[1], desirePosition[0] - originalPosition[0]);
        if (Arrays.equals(previousJumpPos, desirePosition))
            return false;
        if (originalPosition[0] % 2 != desirePosition[0] % 2)
            return false;
        int[] parityDirection = possibleParityMoves[direction];
        int[] oddDirection = possibleOddMoves[direction];
        int counter = 0;
        int distance = 0;
        int xCord = originalPosition[1];
        int yCord = originalPosition[0];
        PlayerId check;
        for (int i = 0; i < 14; i++)
        {
            if (yCord % 2 == 0)
            {
                yCord += parityDirection[0];
                xCord += parityDirection[1];
            } else
            {
                yCord += oddDirection[0];
                xCord += oddDirection[1];
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
            if (desirePosition[0] == yCord && desirePosition[1] == xCord)
                break;
        }
        if(counter != 1)
        {
            return false;
        }
        xCord = originalPosition[1];
        yCord = originalPosition[0];
        for (int i = 0; i < (distance / 2); i++)
        {
            if (yCord % 2 == 0)
            {
                yCord += parityDirection[0];
                xCord += parityDirection[1];
            } else
            {
                yCord += oddDirection[0];
                xCord += oddDirection[1];
            }
        }
        check = board[yCord][xCord];
        return (!check.equals(PlayerId.ZERO));

    }
    @Override
    public PlayerId[][] setUpperPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.FOUR;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setUpperLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperLeftPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.THREE;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setUpperRightPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperRightPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.FIVE;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setBottomLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomLeftPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.TWO;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setBottomRightPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomRightPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.SIX;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setBottomPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.ONE;
        }
        return pools;
    }

    @Override
    public PlayerId[][] setBoardForTwoPlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperPools(pools));
    }

    @Override
    public PlayerId[][] setBoardForThreePlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperLeftPools(setUpperRightPools(pools)));

    }

    @Override
    public PlayerId[][] setBoardForFourPlayers(PlayerId[][] pools)
    {
        return setUpperRightPools(setBottomLeftPools(setBoardForTwoPlayers(pools)));

    }

    @Override
    public PlayerId[][] setBoardForSixPlayers(PlayerId[][] pools)
    {
        return setBoardForFourPlayers(setUpperLeftPools(setBottomRightPools(pools)));
    }

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
}
