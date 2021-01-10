package def;

import java.io.IOException;

public class MainApp
{
    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        try
        {
            Client client = new Client(args[0]);

            client.play();
        }
        catch (IOException e)
        {
            System.out.println("Serwer nieaktywny lub zapełniony");
        }
    }
}
