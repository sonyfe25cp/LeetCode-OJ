import easy.Rob198;
import junit.framework.TestCase;

/**
 * Created by OmarTech on 15-11-10.
 */
public class RobTest extends TestCase{

    public void testRob(){
        Rob198 rob  = new Rob198();
        assertEquals(9, rob.rob(new int[]{1,2,3,4,5}));
        assertEquals(4, rob.rob(new int[]{2,1,1,2}));
        assertEquals(1, rob.rob(new int[]{1}));
        assertEquals(2, rob.rob(new int[]{1,2}));
        assertEquals(39, rob.rob(new int[]{6,3,10,8,2,10,3,5,10,5,3}));
    }
}
