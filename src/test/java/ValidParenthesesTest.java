import easy.ValidParentheses20;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by OmarTech on 15-12-7.
 */
public class ValidParenthesesTest extends TestCase {
    @Test
    public void testIt() {
        String s = "(])";
        ValidParentheses20 validParentheses20 = new ValidParentheses20();
        boolean valid = validParentheses20.isValid(s);
        assertEquals(false, valid);
    }
}
