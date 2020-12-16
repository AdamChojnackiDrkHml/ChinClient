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


    public ArrayList<Pool> setUpperPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : UpperPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(Color.MAGENTA);
                }
            }
        }
        return pools;
    }

    public ArrayList<Pool> setUpperLeftPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : UpperLeftPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(new Color(250, 100,0));
                }
            }
        }
        return pools;
    }
    public ArrayList<Pool> setUpperRightPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : UpperRightPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(Color.BLUE);
                }
            }
        }
        return pools;
    }
    public ArrayList<Pool> setBottomLeftPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : BottomLeftPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(Color.GREEN);
                }
            }
        }
        return pools;
    }
    public ArrayList<Pool> setBottomRightPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : BottomRightPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(Color.YELLOW);
                }
            }
        }
        return pools;
    }
    public ArrayList<Pool> setBottomPools(ArrayList<Pool> pools)
    {
        for(Pool pool : pools)
        {
            for(int[] cords : BottomPools)
            {
                if(pool.xPos == cords[1] && pool.yPos == cords[0])
                {
                    pool.setInsideColor(Color.RED);
                }
            }
        }
        return pools;
    }

    public ArrayList<Pool> setBoardForTwoPlayers(ArrayList<Pool> pools)
    {
        return setBottomPools(setUpperPools(pools));

    }

    public ArrayList<Pool> setBoardForThreePlayers(ArrayList<Pool> pools)
    {
        return setBottomPools(setUpperLeftPools(setBottomRightPools(pools)));

    }
    public ArrayList<Pool> setBoardForFourPlayers(ArrayList<Pool> pools)
    {
        return setUpperRightPools(setBottomLeftPools(setBoardForTwoPlayers(pools)));

    }
    public ArrayList<Pool> setBoardForSixPlayers(ArrayList<Pool> pools)
    {
        return setBoardForFourPlayers(setUpperLeftPools(setBottomRightPools(pools)));
    }

}
