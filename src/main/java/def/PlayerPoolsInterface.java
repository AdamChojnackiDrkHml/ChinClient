package def;

import java.awt.*;
import java.util.ArrayList;

public interface PlayerPoolsInterface
{


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
}
