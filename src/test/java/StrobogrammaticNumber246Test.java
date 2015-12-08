import easy.StrobogrammaticNumber246;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OmarTech on 15-12-7.
 */
public class StrobogrammaticNumber246Test extends TestCase {
    @Test
    public void testIt() {
        StrobogrammaticNumber246 strobogrammaticNumber246 = new StrobogrammaticNumber246();
        Map<String, Boolean> map = new HashMap<>();
        map.put("1", true);
        map.put("14", false);
        map.put("69", true);
        map.put("619", true);
        map.put("818", true);
        map.put("858", false);
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();
            boolean strobogrammatic = strobogrammaticNumber246.isStrobogrammatic(key);
            assertEquals(value.booleanValue(), strobogrammatic);
        }
    }
}
