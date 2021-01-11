package def;

/**
 * Used to handle situations when incorrect field is clicked
 * @author Elzbieta Wisniewska and Adam Chojnacki
 * @version 1.0
 */
public class IncorrectFieldException extends Exception
{
    public IncorrectFieldException(String error)
    {
        super(error);
    }

}
