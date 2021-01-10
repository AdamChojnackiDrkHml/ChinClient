package Testy;

import static org.junit.Assert.*;
import def.*;
import org.junit.Test;

public class StandardGamePolsTest
{
    @Test
    public void setUpBoardTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
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

    @Test
    public void jumpConditionTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
        PlayerId[][] gameBoard = new PlayerId[17][17];
        gameBoard = rules.setBoardForSixPlayers(gameBoard);
        gameBoard[4][6] = PlayerId.ZERO;
        assertTrue(rules.jumpCondition(gameBoard, new int[] {2,7}, new int[] {4, 6}, new int[] {0,0}));
        assertFalse(rules.jumpCondition(gameBoard, new int[] {1,7}, new int[] {4, 6}, new int[] {0,0}));
    }

    @Test
    public void jumpValidTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
        assertTrue(rules.isJumpValid(new int[] {2,7}, new int[] {4, 6}, PlayerId.FOUR));
        assertFalse(rules.isJumpValid(new int[] {1,7}, new int[] {4, 6}, PlayerId.FOUR));
        assertFalse(rules.isJumpValid(new int[] {1,7}, new int[] {7, 6}, PlayerId.FOUR));
    }

    @Test
    public void moveValidTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
        assertTrue(rules.isMoveValid(0, new int[] {0, 8}, new int[] {1, 8}, PlayerId.FOUR));
        assertFalse(rules.isMoveValid(0, new int[] {0, 8}, new int[] {2, 8}, PlayerId.FOUR));
    }

    @Test
    public void enemyBasePositionTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
        assertFalse(rules.checkIfInEnemyBase(new int[] {3, 8}, new int[] {4, 8}, PlayerId.FOUR));
        assertFalse(rules.checkIfInEnemyBase(new int[] {0, 8}, new int[] {1, 8}, PlayerId.ONE));
        assertTrue(rules.checkIfInEnemyBase(new int[] {3, 8}, new int[] {4, 8}, PlayerId.ONE));
    }

    @Test
    public void getDirectionTest()
    {
        PlayerPoolsInterface rules = new StandardGamePools();
        assertEquals(rules.getDirection(4,2), 0);
        assertNotEquals(rules.getDirection(-2, -2), 2);
    }
}
