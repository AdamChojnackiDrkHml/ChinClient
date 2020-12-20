package Testy;

import def.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BoardTest
{

    @Test
    public void testChooseColor()
    {
        Board board = new Board(new Game(PlayerId.ONE, NumberOfPlayers.THREE, new StandardGamePools()));
        assertEquals(board.chooseColor(PlayerId.FIVE), new Color(240, 100,0));
        assertNotEquals(board.chooseColor(PlayerId.ONE), Color.MAGENTA);
    }

}
