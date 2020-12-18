package def;

import java.awt.*;
import java.util.ArrayList;

public interface PlayerPoolsInterface
{
    boolean isMoveValid(int rowNum, Pool originalPosition, Pool desirePosition);
    Pool[][] setUpperPools(Pool[][] pools);
    Pool[][] setUpperLeftPools(Pool[][] pools);
    Pool[][] setUpperRightPools(Pool[][] pools);
    Pool[][] setBottomLeftPools(Pool[][] pools);
    Pool[][] setBottomRightPools(Pool[][] pools);
    Pool[][] setBottomPools(Pool[][] pools);

    Pool[][] setBoardForTwoPlayers(Pool[][] pools);

    Pool[][] setBoardForThreePlayers(Pool[][] pools);
    Pool[][] setBoardForFourPlayers(Pool[][] pools);
    Pool[][] setBoardForSixPlayers(Pool[][] pools);
    Color chooseColor(PlayerId id);
    Pool[][] setUpBoardForPlayers(NumberOfPlayers numOfPlayers, Pool[][] pools);
}
