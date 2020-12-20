package Testy;

import def.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BoardTest
{

    @Test
    public void testChooseColor()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools()));
        assertEquals(board.chooseColor(PlayerId.FIVE), new Color(240, 100,0));
        assertNotEquals(board.chooseColor(PlayerId.ONE), Color.MAGENTA);
    }

    @Test
    public void testChoosePools()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools()));
        assertEquals(board.getPools().get(0).getyPos(), 0);
        assertEquals(board.getPools().get(0).getxPos(), 8);
        assertNotEquals(board.getPools().get(0).getxPos(), 1);
        assertNotEquals(board.getPools().get(0).getyPos(), 5);

    }

    @Test
    public void testContactWithGame()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools()));
        PlayerId[][] prevBoard;
        assertThrows()
    }
}
