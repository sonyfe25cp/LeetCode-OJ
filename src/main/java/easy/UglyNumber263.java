package easy;

/**
 * Created by omar on 15/11/9.
 */
public class UglyNumber263 {
    public boolean isUgly(int num) {
        if (num == 0) {
            return false;
        } else {
            int[] array = new int[]{5, 3, 2};
            for (int i : array) {
                while (num % i == 0) {
                    num = (num / i);
                }
            }
            return num == 1;
        }
    }
}
