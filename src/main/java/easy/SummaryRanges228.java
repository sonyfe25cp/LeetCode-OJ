package easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarTech on 15-11-13.
 */
public class SummaryRanges228 {
    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return list;
        }
        int begin = 0;
        int last = 0;
        while (last < nums.length) {
            if (last + 1 < nums.length && nums[last + 1] == nums[last] + 1) {
                last++;
            } else {
                if (begin == last) {
                    list.add(nums[last] + "");
                } else {
                    list.add(nums[begin] + "->" + nums[last]);
                }
                last++;
                begin = last;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        SummaryRanges228 summaryRanges = new SummaryRanges228();
        List<String> strings = summaryRanges.summaryRanges(new int[]{-2147483648, -2147483647, 1, 2, 3, 7});
        System.out.println(strings);
    }
}
