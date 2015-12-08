import junit.framework.TestCase;
import medium.StrobogrammaticNumber247;
import org.junit.Test;

import java.util.List;

/**
 * Created by OmarTech on 15-12-7.
 */
public class StrobogrammaticNumber247Test extends TestCase {
    @Test
    public void testIt(){
        StrobogrammaticNumber247 strobogrammaticNumber247 = new StrobogrammaticNumber247();
        int n = 3;
        List<String> strobogrammatic = strobogrammaticNumber247.findStrobogrammatic(n);
        for(String s : strobogrammatic){
            System.out.println(s);
        }
    }
}
