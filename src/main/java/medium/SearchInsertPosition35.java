package medium;
/**
 * Created by omar on 15/11/17.
 */
public class SearchInsertPosition35 {
    public int searchInsert(int[] nums, int target) {
        int begin = 0;
        int end = nums.length - 1;
        int pos = 0;
        while (begin <= end) {
            if (nums[begin] >= target) {
                return begin;
            } else if (nums[end] == target) {
                return end;
            } else if (nums[end] < target) {
                return end + 1;
            } else if (nums[begin] < target && nums[begin + 1] > target) {
                return begin + 1;
            } else {
                int mid = begin + (end - begin) / 2;
                if (nums[mid] >= target) {
                    end = mid;
                } else {
                    begin = mid;
                    pos += mid;
                }
            }
        }
        return pos;
    }
}
