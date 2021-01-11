package def;

/**
 * Interface containing functions used to set rules for the game. It can be later implemented to easily modify rules.
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 4.0
 */
public interface GameRulesInterface
{
    int BoardSize = 17;
    int NumberOfPoolsInEnemyBase = 10;

    boolean checkIfInEnemyBase(int[] originalPosition, int[] desirePosition, PlayerId id);
    boolean isMoveValid(int[] originalPosition, int[] desirePosition, PlayerId id);
    boolean isJumpValid(int[] originalPosition, int[] desirePosition, PlayerId id);
    boolean jumpCondition(PlayerId[][] board, int[] originalPosition, int[] desirePosition, int[] previousJumpPos);
    PlayerId[][] setUpperPools(PlayerId[][] pools);
    PlayerId[][] setUpperLeftPools(PlayerId[][] pools);
    PlayerId[][] setUpperRightPools(PlayerId[][] pools);
    PlayerId[][] setBottomLeftPools(PlayerId[][] pools);
    PlayerId[][] setBottomRightPools(PlayerId[][] pools);
    PlayerId[][] setBottomPools(PlayerId[][] pools);

    PlayerId[][] setBoardForTwoPlayers(PlayerId[][] pools);

    PlayerId[][] setBoardForThreePlayers(PlayerId[][] pools);
    PlayerId[][] setBoardForFourPlayers(PlayerId[][] pools);
    PlayerId[][] setBoardForSixPlayers(PlayerId[][] pools);

    PlayerId[][] setUpBoardForPlayers(NumberOfPlayers numOfPlayers,PlayerId[][] pools);

    int getDirection(int moveX, int moveY);
}
