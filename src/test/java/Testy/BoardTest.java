package Testy;

import def.*;
import org.junit.Test;

import java.awt.*;


import static org.junit.Assert.*;

public class BoardTest
{

    /**
     * This test test if chooseColor function works properly
     */
    @Test
    public void testChooseColor()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules()));
        assertEquals(board.chooseColor(PlayerId.FIVE), new Color(240, 100,0));
        assertNotEquals(board.chooseColor(PlayerId.ONE), Color.MAGENTA);
    }

    /**
     * This test test if choosePools function works properly
     */
    @Test
    public void testChoosePools()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules()));
        assertEquals(board.getPools().get(0).getYPos(), 0);
        assertEquals(board.getPools().get(0).getXPos(), 8);
        assertNotEquals(board.getPools().get(0).getXPos(), 1);
        assertNotEquals(board.getPools().get(0).getYPos(), 5);
    }


    /**
     * This test test if game successfully passes information to the game
     */
    @Test
    public void testContactWithGame() throws IncorrectFieldException
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGameRules()));
        board.getGame().getMessage("YOUR_MOVE");
        board.contactWithGame(341,664);
        assertArrayEquals(board.getGame().getChosen(), new int[]{16, 8});
    }
}
