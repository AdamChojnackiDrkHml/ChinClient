package def;


public interface PlayerPoolsInterface
{
    boolean checkIfInEnemyBase(int[] originalPosition, int[] desirePosition, PlayerId id);
    boolean isMoveValid(int rowNum, int[] originalPosition, int[] desirePosition, PlayerId id);
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
}
