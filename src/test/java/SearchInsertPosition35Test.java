import junit.framework.TestCase;
import medium.SearchInsertPosition35;
import org.junit.Test;


public class SearchInsertPosition35Test extends TestCase {

    @Test
    public void testTestInsert() {
        SearchInsertPosition35 sip = new SearchInsertPosition35();
        assertEquals(1, sip.searchInsert(new int[]{1}, 2));
        System.out.println("test 0 over");
        assertEquals(1, sip.searchInsert(new int[]{1, 3, 5, 6}, 2));
        System.out.println("test 1 over");
        assertEquals(2, sip.searchInsert(new int[]{1, 3, 5, 6}, 5));
        System.out.println("test 2 over");
        assertEquals(4, sip.searchInsert(new int[]{1, 3, 5, 6}, 7));
        System.out.println("test 3 over");
        assertEquals(0, sip.searchInsert(new int[]{1, 3, 5, 6}, 0));
        System.out.println("test 4 over");
        assertEquals(9, sip.searchInsert(new int[]{1, 3, 5, 6, 7, 8, 9, 10, 11, 15}, 12));
        System.out.println("test 5 over");
    }

}