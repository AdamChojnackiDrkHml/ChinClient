package Testy;

import def.*;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;


public class CommunicationCenterTest
{
    @Test
    public void interpretMessageTest() throws ConnectionException
    {
        PrintWriter out = mock(PrintWriter.class);
        Scanner scanner = new Scanner("WINNER ONE");
        CommunicationCenter communicationCenter = new CommunicationCenter(out, new Board(new Game(PlayerId.FOUR, NumberOfPlayers.SIX, new StandardGamePools())), scanner);
        assertEquals(communicationCenter.interpretMessage(), "WINNER ONE");
    }
}
