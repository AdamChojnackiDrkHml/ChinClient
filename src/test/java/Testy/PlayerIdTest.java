package Testy;

import def.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlayerIdTest
{
    /**
     * This test checks if getInt function returns expected value
     */
    @Test
    public void getIntTest()
    {
        assertEquals(PlayerId.getInt(PlayerId.FOUR), 4);
        assertNotEquals(PlayerId.getInt(PlayerId.FIVE), 2);
    }
}
