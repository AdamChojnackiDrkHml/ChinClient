package def;

/**
 * Exception I used to deal with server connection
 * @author Adam Chojnacki i Ela Wiśniewska
 * @version 1.0
 */
public class ConnectionException extends Exception
{
    ConnectionException(String message)
    {
        super(message);
    }
}
