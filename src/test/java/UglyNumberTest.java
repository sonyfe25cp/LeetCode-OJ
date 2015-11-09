import easy.UglyNumber263;
import junit.framework.TestCase;

/**
 * Created by omar on 15/11/9.
 */
public class UglyNumberTest extends TestCase {
    public void testIsUgly(){
        UglyNumber263 uglyNumber = new UglyNumber263();

        assertEquals(false, uglyNumber.isUgly(0));
        assertEquals(true, uglyNumber.isUgly(1));
        assertEquals(true, uglyNumber.isUgly(2));
        assertEquals(true, uglyNumber.isUgly(3));
        assertEquals(true, uglyNumber.isUgly(5));
        assertEquals(false, uglyNumber.isUgly(14));
        assertEquals(false, uglyNumber.isUgly(21));
        assertEquals(false, uglyNumber.isUgly(42));
        assertEquals(true, uglyNumber.isUgly(2123366400));
    }
}
