package def;

/**
 * Enum used to easily control which player takes actions and to properly set up board.
 * It also contains function that returns number value of enum
 * @author Adam Chojnacki i Ela Wiśniewska
 * @version 2.0
 */
public enum PlayerId
{
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, NULL;

    public static int getInt(PlayerId id)
    {
        switch(id)
        {
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
        }
        return 0;
    }
}
