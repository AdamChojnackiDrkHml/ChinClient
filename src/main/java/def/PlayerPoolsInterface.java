package def;


public interface PlayerPoolsInterface
{
    boolean isMoveValid(int rowNum, int[] originalPosition, int[] desirePosition);
    boolean isJumpValid(int[] originalPosition, int[] desirePosition);
    boolean jumpCondition(PlayerId[][] board, int[] originalPosition, int[] desirePosition );
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
