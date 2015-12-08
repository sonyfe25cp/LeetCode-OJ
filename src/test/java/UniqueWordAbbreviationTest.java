import easy.UniqueWordAbbreviation288;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by OmarTech on 15-12-7.
 */
public class UniqueWordAbbreviationTest extends TestCase {
    @Test
    public void testIt(){
        String[] s = new String[]{"deer", "door", "cat", "make"};
        UniqueWordAbbreviation288 uniqueWordAbbreviation288 = new UniqueWordAbbreviation288();
        uniqueWordAbbreviation288.findUnique(s);
    }

}
