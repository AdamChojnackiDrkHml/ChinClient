package def;

import java.awt.*;
import java.util.ArrayList;

public class StandardGamePools implements PlayerPoolsInterface
{
    int[][] UpperPools = {{0,8}, {1,7}, {1,8}, {2,7}, {2,8}, {2,9}, {3,6}, {3,7}, {3,8}, {3,9}};
    int[][] UpperLeftPools = {{4,2}, {4,3}, {4,4}, {4,5}, {5,2}, {5,3}, {5,4}, {6,3}, {6,4}, {7,3}};
    int[][] UpperRightPools = {{4,11}, {4,12}, {4,13}, {4,14}, {5,11}, {5,12}, {5,13}, {6,12}, {6,13}, {7,12}};
    int[][] BottomLeftPools = {{9,3}, {10,3}, {10,4}, {11,2}, {11,3}, {11,4}, {12,2}, {12,3}, {12,4}, {12,5}};
    int[][] BottomRightPools = {{12,11}, {12,12}, {12,13}, {12,14}, {11,11}, {11,12}, {11,13}, {10,12}, {10,13}, {9,12}};
    int[][] BottomPools = {{16,8}, {15,7}, {15,8}, {14,7}, {14,8}, {14,9}, {13,6}, {13,7}, {13,8}, {13,9}};
    int[][] possibleParityMoves = {{0, 1},{0, -1},{-1, 0},{-1, -1},{1, -1},{1,0}};
    int[][] possibleOddMoves = {{0,1},{0,-1},{1,1},{1,0},{-1,0},{-1,1}};


    @Override
    public boolean isMoveValid(int rowNum, Pool originalPosition, Pool desirePosition)
    {
        int moveX = desirePosition.xPos - originalPosition.xPos;
        int moveY = desirePosition.yPos - originalPosition.yPos;
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

    public Pool[][] setUpperPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : UpperPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(Color.MAGENTA);
                        }
                    }
                }
            }
        }
        return pools;
    }

    public Pool[][] setUpperLeftPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : UpperLeftPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(Color.GREEN);
                        }
                    }
                }
            }
        }
        return pools;
    }
    public Pool[][] setUpperRightPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : UpperRightPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(new Color(240, 100, 0));
                        }
                    }
                }
            }
        }
        return pools;
    }
    public Pool[][] setBottomLeftPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : BottomLeftPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(Color.YELLOW);
                        }
                    }
                }
            }
        }
        return pools;
    }
    public Pool[][] setBottomRightPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : BottomRightPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(Color.BLUE);
                        }
                    }
                }
            }
        }
        return pools;
    }
    public Pool[][] setBottomPools(Pool[][] pools)
    {
        for(Pool[] row : pools)
        {
            for(Pool pool : row)
            {
                if(pool != null)
                {
                    for (int[] cords : BottomPools)
                    {
                        if (pool.xPos == cords[1] && pool.yPos == cords[0])
                        {
                            pool.setInsideColor(Color.RED);
                        }
                    }
                }
            }
        }
        return pools;
    }

    public Pool[][] setBoardForTwoPlayers(Pool[][] pools)
    {
        return setBottomPools(setUpperPools(pools));

    }

    public Pool[][] setBoardForThreePlayers(Pool[][] pools)
    {
        return setBottomPools(setUpperLeftPools(setUpperRightPools(pools)));

    }
    public Pool[][] setBoardForFourPlayers(Pool[][] pools)
    {
        return setUpperRightPools(setBottomLeftPools(setBoardForTwoPlayers(pools)));

    }
    public Pool[][] setBoardForSixPlayers(Pool[][] pools)
    {
        return setBoardForFourPlayers(setUpperLeftPools(setBottomRightPools(pools)));
    }

}
