package medium;

/**
 * Given an unsorted array nums, reorder it in-place
 * such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 * For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 * Created by OmarTech on 15-12-7.
 */
public class WiggleSort280 {
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length < 1) {
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            if ((i % 2 == 1 && nums[i] < nums[i - 1]) || (i % 2 == 0 && nums[i] > nums[i - 1])) {
                int tmp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int nums[] = new int[]{3, 5, 6, 7, 8, 9, 10};
        WiggleSort280 wiggleSort280 = new WiggleSort280();
        wiggleSort280.wiggleSort(nums);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }
}
