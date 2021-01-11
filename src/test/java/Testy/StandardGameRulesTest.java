package Testy;

import static org.junit.Assert.*;
import def.*;
import org.junit.Test;

public class StandardGameRulesTest
{
    /**
     * This test checks if board is set correctly for given number of players
     */
    @Test
    public void setUpBoardTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        PlayerId[][] gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForSixPlayers(gameBoard);
        assertEquals(gameBoard[0][8], PlayerId.FOUR);

        gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForThreePlayers(gameBoard);
        assertNull(gameBoard[0][8]);
        assertNotEquals(gameBoard[16][8], PlayerId.NULL);

        gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForTwoPlayers(gameBoard);
        assertNotNull(gameBoard[0][8]);
        assertNull(gameBoard[12][2]);

        gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForFourPlayers(gameBoard);
        assertNull(gameBoard[4][2]);
        assertNotEquals(gameBoard[4][13], PlayerId.NULL);
    }

    /**
     * This test checks if jumpCondition returns expected values
     */
    @Test
    public void jumpConditionTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        PlayerId[][] gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForSixPlayers(gameBoard);
        gameBoard[4][6] = PlayerId.ZERO;
        assertTrue(rules.jumpCondition(gameBoard, new int[] {2,7}, new int[] {4, 6}, new int[] {0,0}));
        assertFalse(rules.jumpCondition(gameBoard, new int[] {1,7}, new int[] {4, 6}, new int[] {0,0}));
    }

    /**
     * This test is designed to check if this function successfully finds incorrect jump moves
     */
    @Test
    public void jumpValidTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        assertTrue(rules.isJumpValid(new int[] {2,7}, new int[] {4, 6}, PlayerId.FOUR));
        assertFalse(rules.isJumpValid(new int[] {1,7}, new int[] {4, 6}, PlayerId.FOUR));
        assertFalse(rules.isJumpValid(new int[] {1,7}, new int[] {7, 6}, PlayerId.FOUR));
    }

    /**
     * This test checks if basic move validation works
     */
    @Test
    public void moveValidTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        assertTrue(rules.isMoveValid(new int[] {0, 8}, new int[] {1, 8}, PlayerId.FOUR));
        assertFalse(rules.isMoveValid(new int[] {0, 8}, new int[] {2, 8}, PlayerId.FOUR));
    }

    /**
     * This test checks if preventing player from exiting enemy base works
     */
    @Test
    public void enemyBasePositionTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        assertFalse(rules.checkIfInEnemyBase(new int[] {3, 8}, new int[] {4, 8}, PlayerId.FOUR));
        assertFalse(rules.checkIfInEnemyBase(new int[] {0, 8}, new int[] {1, 8}, PlayerId.ONE));
        assertTrue(rules.checkIfInEnemyBase(new int[] {3, 8}, new int[] {4, 8}, PlayerId.ONE));
    }

    /**
     * This test is designed to check if this function return correct direction
     */
    @Test
    public void getDirectionTest()
    {
        GameRulesInterface rules = new StandardGameRules();
        assertEquals(rules.getDirection(4,2), 0);
        assertNotEquals(rules.getDirection(-2, -2), 2);
    }
}
