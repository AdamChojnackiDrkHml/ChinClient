package def;

import java.awt.*;
import java.util.ArrayList;

public interface PlayerPoolsInterface
{


    ArrayList<Pool> setUpperPools(ArrayList<Pool> pools);
    ArrayList<Pool> setUpperLeftPools(ArrayList<Pool> pools);
    ArrayList<Pool> setUpperRightPools(ArrayList<Pool> pools);
    ArrayList<Pool> setBottomLeftPools(ArrayList<Pool> pools);
    ArrayList<Pool> setBottomRightPools(ArrayList<Pool> pools);
    ArrayList<Pool> setBottomPools(ArrayList<Pool> pools);

    ArrayList<Pool> setBoardForTwoPlayers(ArrayList<Pool> pools);

    ArrayList<Pool> setBoardForThreePlayers(ArrayList<Pool> pools);
    ArrayList<Pool> setBoardForFourPlayers(ArrayList<Pool> pools);
    ArrayList<Pool> setBoardForSixPlayers(ArrayList<Pool> pools);
}
