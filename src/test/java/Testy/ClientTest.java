package Testy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import def.*;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class ClientTest
{
    @Test
    public void testFrame() throws Exception
    {
        try
        {
            Client client = new Client("192.168.0.164");
            assertEquals(client.getSize().getWidth(), 40 * 18, 0.5);
            assertNotEquals(client.getSize().getHeight(), 40 * 18, 0.5);
            assertTrue(client.isVisible());
            assertFalse(client.isResizable());
        }
        catch (IOException ignored)
        {

        }
    }


}
