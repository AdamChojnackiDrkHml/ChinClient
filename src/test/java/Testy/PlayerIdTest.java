package Testy;

import def.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlayerIdTest
{
    @Test
    public void getIntTest()
    {
        assertEquals(PlayerId.getInt(PlayerId.FOUR), 4);
        assertNotEquals(PlayerId.getInt(PlayerId.FIVE), 2);
    }
}
