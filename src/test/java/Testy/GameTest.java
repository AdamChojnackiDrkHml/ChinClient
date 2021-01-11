package Testy;

import def.*;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameTest
{

    /**
     * This test test if function choosePlayersPools does correct job
     */
    @Test
    public void ChoosePlayersPoolsTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        assertEquals(game.getGameBoard()[0][8], PlayerId.ZERO);
        assertEquals(game.getGameBoard()[16][9], PlayerId.NULL);

        game = new Game(PlayerId.ONE, NumberOfPlayers.FOUR, new StandardGameRules());
        assertNotEquals(game.getGameBoard()[12][2], PlayerId.ZERO);
        assertEquals(game.getGameBoard()[0][7], PlayerId.NULL);
    }

    /**
     * This function tests if pool validation works properly
     */
    @Test
    public void ValidPoolTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        assertEquals(game.getGameBoard()[0][0], PlayerId.NULL);
        assertNotEquals(game.getGameBoard()[8][8], PlayerId.NULL);
    }

    /**
     * This function checks if endTurn function correctly sets all flags
     */
    @Test
    public void EndTurnTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        PrintWriter out = mock(PrintWriter.class);
        Scanner in = new Scanner("AAAA");
        CommunicationCenter communicationCenter = new CommunicationCenter( out, new Board(game), in);
        game.getMessage("YOUR_MOVE");
        assertTrue(game.isItMyTurn());
        game.endTurn();
        assertFalse(game.isItMyTurn());
        assertFalse(game.canIMove());
    }

    /**
     * This test checks if player winning situation is handled
     */
    @Test
    public void winTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        assertFalse(game.isHaveIWon());
        game.getMessage("WINNER ONE");
        assertFalse(game.isItMyTurn());
        assertFalse(game.canIMove());
        assertTrue(game.isHaveIWon());
    }

    /**
     * This test test if decide function works as it should
     */
    @Test
    public void decideTest() throws IncorrectFieldException
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        game.getMessage("YOUR_MOVE");
        game.decide(new int[] {16, 8});
        assertTrue(game.canIMove());
        assertTrue(game.isItMyTurn());
        assertArrayEquals(game.getChosen(), new int[] {16,8});

        game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules());
        game.decide(new int[] {16, 8});
        assertArrayEquals(game.getChosen(), new int[] {0,0});
    }


}
