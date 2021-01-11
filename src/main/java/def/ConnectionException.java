package def;

/**
 * Exception I used to deal with server connection
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 1.0
 */
public class ConnectionException extends Exception
{
    ConnectionException(String message)
    {
        super(message);
    }
}
