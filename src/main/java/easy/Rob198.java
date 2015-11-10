package easy;

/**
 * Created by OmarTech on 15-11-10.
 */
public class Rob198 {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else {
            int length = nums.length;
            if (length == 1) {
                return nums[0];
            } else {
                int[] vector = new int[length];
                vector[0] = nums[0];
                vector[1] = Math.max(nums[0], nums[1]);
                for (int i = 2; i < length; i++) {
                    vector[i] = Math.max(vector[i - 2] + nums[i], vector[i - 1]);
                }
                return vector[length - 1];
            }
        }
    }
}
