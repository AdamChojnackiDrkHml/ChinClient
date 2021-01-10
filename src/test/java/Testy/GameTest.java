package Testy;

import def.*;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameTest
{

    @Test
    public void ChoosePlayersPoolsTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        assertEquals(game.getGameBoard()[0][8], PlayerId.ZERO);
        assertEquals(game.getGameBoard()[16][9], PlayerId.NULL);

        game = new Game(PlayerId.ONE, NumberOfPlayers.FOUR, new StandardGamePools());
        assertNotEquals(game.getGameBoard()[12][2], PlayerId.ZERO);
        assertEquals(game.getGameBoard()[0][7], PlayerId.NULL);
    }
    @Test
    public void ValidPoolTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        assertEquals(game.getGameBoard()[0][0], PlayerId.NULL);
        assertNotEquals(game.getGameBoard()[8][8], PlayerId.NULL);
    }

    @Test
    public void EndTurnTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        PrintWriter out = mock(PrintWriter.class);
        Scanner in = new Scanner("AAAA");
        CommunicationCenter communicationCenter = new CommunicationCenter( out, new Board(game), in);
        game.getMessage("YOUR_MOVE");
        assertTrue(game.isItMyTurn());
        game.endTurn();
        assertFalse(game.isItMyTurn());
        assertFalse(game.canIMove());
    }

    @Test
    public void winTest()
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        assertFalse(game.isHaveIWon());
        game.getMessage("WINNER ONE");
        assertFalse(game.isItMyTurn());
        assertFalse(game.canIMove());
        assertTrue(game.isHaveIWon());
    }

    @Test
    public void decideTest() throws IncorrectFieldException
    {
        Game game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        game.getMessage("YOUR_MOVE");
        game.decide(new int[] {16, 8});
        assertTrue(game.canIMove());
        assertTrue(game.isItMyTurn());
        assertArrayEquals(game.getChosen(), new int[] {16,8});

        game = new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools());
        game.decide(new int[] {16, 8});
        assertArrayEquals(game.getChosen(), new int[] {0,0});
    }


}
