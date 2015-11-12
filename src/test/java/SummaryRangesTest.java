import easy.SummaryRanges228;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by OmarTech on 15-11-13.
 */
public class SummaryRangesTest extends TestCase {
    @Test
    public void testRange() {
        SummaryRanges228 summaryRanges = new SummaryRanges228();
        List<String> strings = summaryRanges.summaryRanges(new int[]{-2147483648, -2147483647, 1, 2, 3, 7});
        List<String> strings1 = Arrays.asList("-2147483648->-2147483647", "1->3", "7");
        assertEquals(strings1, strings);
    }
}
