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

    int[][] possibleParityMoves = {{0, 1},{0, -1},{-1, 0},{-1, -1},{1, -1},{1,0}};
    int[][] possibleOddMoves = {{0,1},{0,-1},{1,1},{1,0},{-1,0},{-1,1}};

    int[][] possibleJumps = { {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {0,2}, {0,-2}};


    @Override
    public boolean checkIfInEnemyBase(int[] originalPosition, int[] desirePosition, PlayerId id)
    {
        int[][] base;
        int enemyId = 0;
        boolean isOriginInBase = false;
        boolean isDestInBase = false;
        switch(id)
        {
            case ONE:
                enemyId = 4;
                break;
            case TWO:
                enemyId = 5;
                break;
            case THREE:
                enemyId = 6;
                break;
            case FOUR:
                enemyId = 1;
                break;
            case FIVE:
                enemyId = 2;
                break;
            case SIX:
                enemyId = 3;
                break;
        }
        enemyId -= 1;
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
        int[] direction = new int[2];
        if(moveX > 0)
        {
            if(moveY > 0)
            {
                direction = possibleJumps[0];

            }
            else if(moveY < 0)
            {
                direction = possibleJumps[2];
            }
            else
            {
                direction = possibleJumps[4];
            }
        }
        else if(moveX < 0)
        {
            if(moveY > 0)
            {
                direction = possibleJumps[1];

            }
            else if(moveY < 0)
            {
                direction = possibleJumps[3];
            }
            else
            {
                direction = possibleJumps[5];
            }
        }


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
        if(Arrays.equals(previousJumpPos, desirePosition))
            return false;
        if (originalPosition[0] % 2 != desirePosition[0] % 2)
            return false;
        int yCord = (originalPosition[0] + desirePosition[0]) / 2;
        int xCord = (originalPosition[1] + desirePosition[1]) / 2;
        if (originalPosition[0] % 2 == 1 && (originalPosition[1] + desirePosition[1]) % 2 == 1)
        {
            xCord++;
        }
        PlayerId check = board[yCord][xCord];
        boolean one = check.equals(PlayerId.NULL);
        boolean two = check.equals(PlayerId.ZERO);
        return !(one || two);
    }
    public PlayerId[][] setUpperPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.FOUR;
        }
        return pools;
    }

    public PlayerId[][] setUpperLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperLeftPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.THREE;
        }
        return pools;
    }
    public PlayerId[][] setUpperRightPools(PlayerId[][] pools)
    {
        for (int[] cords : UpperRightPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.FIVE;
        }
        return pools;
    }
    public PlayerId[][] setBottomLeftPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomLeftPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.TWO;
        }
        return pools;
    }
    public PlayerId[][] setBottomRightPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomRightPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.SIX;
        }
        return pools;
    }
    public PlayerId[][] setBottomPools(PlayerId[][] pools)
    {
        for (int[] cords : BottomPools)
        {
            pools[cords[0]][cords[1]] = PlayerId.ONE;
        }
        return pools;
    }

    public PlayerId[][] setBoardForTwoPlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperPools(pools));
    }

    public PlayerId[][] setBoardForThreePlayers(PlayerId[][] pools)
    {
        return setBottomPools(setUpperLeftPools(setUpperRightPools(pools)));

    }
    public PlayerId[][] setBoardForFourPlayers(PlayerId[][] pools)
    {
        return setUpperRightPools(setBottomLeftPools(setBoardForTwoPlayers(pools)));

    }
    public PlayerId[][] setBoardForSixPlayers(PlayerId[][] pools)
    {
        return setBoardForFourPlayers(setUpperLeftPools(setBottomRightPools(pools)));
    }

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
